// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');

// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

const OPERATION_TYPE = {
    UPDATE: {value: 0},
    ADD: {value: 1},
    REMOVE: {value: -1},
};

exports.newQuestionsAdded = functions.database.ref('/users/{pushId}/betsArray/')
    .onWrite(event => {
        const prev = event.data.previous.val();
        const curr = event.data.current.val();
		console.log("previous value ---------->  " + prev)
        console.log("currrent value ---------->  " + curr)


        let operationType = getOperationType(curr, prev);

        const ref = admin.database().ref("bets/{curr}/participants/");
        ref.once("value", function (snapshot) {
            const totalQuestions = 1;
            const reference = "hola"

            admin.database().ref().update({$reference : totalQuestions});

            console.log("Previous val: " + parseInt(snapshot.val()) + ", Next val: " + totalQuestions);
        }, function (errorObject) {
            console.log(errorObject.message);
        });

        // You must return a Promise when performing asynchronous tasks inside a Functions such as
        // writing to the Firebase Realtime Database.
        // Setting an "uppercase" sibling in the Realtime Database returns a Promise.
        return true;
    });

function getOperationType(curr, prev) {
    let operationType = OPERATION_TYPE.UPDATE;
    if (curr && !prev) {
        // Value created
        operationType = OPERATION_TYPE.ADD;
    } else if (!curr && prev) {
        // Value removed
        operationType = OPERATION_TYPE.REMOVE;
    } else {
        // Value updated
    }

    return operationType;
}

function nextQuestionCountValue(operationType, previsVal) {
    let totalQuestions = parseInt(previsVal);

    if (operationType.value === OPERATION_TYPE.ADD.value) {
        totalQuestions = totalQuestions + 1;
    } else if (operationType.value === OPERATION_TYPE.REMOVE.value) {
        totalQuestions = totalQuestions - 1;
    }

    return totalQuestions;
}

