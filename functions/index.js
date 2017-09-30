// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');

// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.updateUserMoney = functions.database.ref('/users/{uid}/moneyToUpdate/')
    .onWrite(event => {
        //const prev = event.data.previous.val();
        const curr = event.data.current.val();
        const prev = event.data.previous.val();
        event.data.ref.parent.child('moneyToUpdate').set(0)

		console.log("previous value ---------->  " + prev)
        console.log("currrent value ---------->  " + curr)

        //let operationType = getOperationType(curr, prev);

        const ref = admin.database().ref("users/" + event.params.uid);
        console.log("userID------>" + event.params.uid)

        ref.once("value", function (snapshot) {
        	console.log("snapshot--------->    " + snapshot.val()['money'])
            const total = snapshot.val()['money'] + curr;
            console.log("total money ======" + total)

            event.data.ref.parent.child('money').set(total)

            //admin.database().ref().update({money: 0});

        }, function (errorObject) {
            console.log(errorObject.message);
        });

        // You must return a Promise when performing asynchronous tasks inside a Functions such as
        // writing to the Firebase Realtime Database.
        // Setting an "uppercase" sibling in the Realtime Database returns a Promise.
        return true;
    });

exports.updateFirstParticipantsArray = functions.database.ref('/users/{uid}/bets/{betId}/option/')
	.onWrite(event => {
	
		const curr = event.data.current.val()
		console.log("current ------------>" + curr)

		const uid = event.params.uid
		console.log("userID --------->" + uid)

		const betId = event.params.betId
		console.log("betId ---------->" + betId)
		
		if (betId == null || curr == null) {
			console.log("null objects")
			return;
		}

		if (curr == 'option1') {
	        const ref = admin.database().ref("groupal_bets/" +  event.params.betId + "/option1/");
	        console.log("option1")
	        addParticipant(ref, uid, betId, curr)
	    } else {
	    	const ref = admin.database().ref("groupal_bets/" +  event.params.betId + "/option2/");
	        console.log("option2")
	        addParticipant(ref, uid, betId, curr)

	    }
	});

function addParticipant(ref, uid, betId, option) {
    ref.once("value", function (snapshot) {
        var prevBets = snapshot.val()['participants'];        

        if (prevBets == null) {
        	prevBets = []
        	prevBets.push(uid)
        	console.log("prevBets ========" + prevBets)
        	console.log("first participant---->" + uid)
        	const reference = "/groupal_bets/"+ betId + "/" + option;
        	console.log("reference-------->" + reference)
        	admin.database().ref(reference + "/participants/").set(prevBets);
        	admin.database().ref(reference + "/numberParticipants/").set(1)

        	return;
        }

            prevBets.push(uid)
        	console.log("prevBets ========" + prevBets)
        	console.log("first participant---->" + uid)
        	const reference = "/groupal_bets/"+ betId + "/" + option;
        	console.log("reference-------->" + reference)
        	var numberParticipants = snapshot.val()['numberParticipants'] + 1;
        	admin.database().ref(reference + "/participants/").set(prevBets);
        	admin.database().ref(reference + "/numberParticipants/").set(numberParticipants)
        	admin.database().ref("/groupal_bets/" + betId + "/totalParticipants/").set()

    }, function (errorObject) {
        console.log(errorObject.message);
    });
}


