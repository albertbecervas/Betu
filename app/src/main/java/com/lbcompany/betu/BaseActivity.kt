package com.lbcompany.betu

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.firebase.client.Firebase
import com.google.firebase.database.*
import com.lbcompany.betu.model.User
import com.lbcompany.betu.ui.LoginActivity
import com.lbcompany.betu.ui.MainActivity
import com.lbcompany.betu.ui.ProfileActivity
import com.lbcompany.betu.utils.AppSharedPreferences
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.jetbrains.anko.toast
import java.io.ByteArrayOutputStream

abstract class BaseActivity : AppCompatActivity() {

    private val PICK_IMAGE = 1

    private lateinit var headerResult: AccountHeader
    private lateinit var profileDrawerItem: ProfileDrawerItem

    private lateinit var mPrefs: AppSharedPreferences
    private lateinit var mDatabase: DatabaseReference

    abstract fun getView(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getView())

        mPrefs = AppSharedPreferences(this)
        mDatabase = FirebaseDatabase.getInstance().reference

        setUser()
        setToolbar()
        setDrawer()

        mDatabase.child("users").child(mPrefs.getUserID()).child("money").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(dS: DataSnapshot?) {
                val doubleValue = dS?.value
                val float = java.lang.Float.valueOf(doubleValue.toString())
                mPrefs.setUserMoney(float)
                setUser()
            }
        })
    }

    private fun setUser() {
        if (mPrefs.getUsername() != "null") login.text = "${mPrefs.getUsername()} | ${mPrefs.getUserMoney()}€"
    }

    private fun setToolbar() {
        login.setOnClickListener {
            if (mPrefs.getUserID() != "null") {
                startActivity(Intent(this@BaseActivity, ProfileActivity::class.java))
                return@setOnClickListener
            }
            startActivity(Intent(this@BaseActivity, LoginActivity::class.java))
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    private fun setDrawer() {
        val item1 = PrimaryDrawerItem().withIdentifier(1).withName("My account")
        item1.withIcon(R.drawable.avatar)
        val item3 = SecondaryDrawerItem().withIdentifier(3).withName("Logout")
        item3.withIcon(R.drawable.logout)
        item3.withTextColorRes(R.color.colorPrimaryDark)
        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {
            override fun set(imageView: ImageView?, uri: Uri?, placeholder: Drawable?) {
                /*if (mPrefs.getImageUrl() != null) {
                    Glide.with(imageView.getContext()).load(mPrefs.getImageUrl()).into(imageView);
                }*/
            }

            override fun cancel(imageView: ImageView?) {
                super.cancel(imageView)
            }

            override fun placeholder(ctx: Context, tag: String?): Drawable =
                    super.placeholder(ctx, tag)
        })


        profileDrawerItem = ProfileDrawerItem().withName(mPrefs.getName()).withEmail(mPrefs.getUsername()).withIcon(R.drawable.profile).withIdentifier(4)

        // Create the AccountHeader
        headerResult = AccountHeaderBuilder()
                .withActivity(this@BaseActivity)
                .withHeaderBackground(R.color.colorPrimary)
                .withOnAccountHeaderProfileImageListener(object : AccountHeader.OnAccountHeaderProfileImageListener {
                    override fun onProfileImageClick(view: View, profile: IProfile<*>, current: Boolean): Boolean {
                        if (profile.identifier == 4L) {
//                            val intent = Intent()
//                            intent.type = "image/*"
//                            intent.action = Intent.ACTION_GET_CONTENT
//                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
                        }
                        return true
                    }

                    override fun onProfileImageLongClick(view: View, profile: IProfile<*>, current: Boolean): Boolean =
                            false
                })
                .addProfiles(
                        profileDrawerItem
                )
                .build()

        //Now create your drawer and pass the AccountHeader.Result
        DrawerBuilder()
                .withActivity(this@BaseActivity)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .withSliderBackgroundColorRes(R.color.material_grey_600)
                .addDrawerItems(
                        item1,
                        DividerDrawerItem(),
                        item3
                )
                .withOnDrawerItemClickListener { _, _, drawerItem ->
                    when (drawerItem.identifier.toInt()) {
                        1 -> {
                            startActivity(Intent(this@BaseActivity, ProfileActivity::class.java))
                        }
                        3 -> {
                            val mOnClickListener = View.OnClickListener {
                                toast("logged out")
                                mPrefs.logout()
                                User.username = null
                                startActivity(Intent(this@BaseActivity, MainActivity::class.java))
                                finish()
                            }
                            Snackbar.make(findViewById(android.R.id.content), "Are you sure you wnat to log out?", Snackbar.LENGTH_LONG)
                                    .setAction("Log out", mOnClickListener)
                                    .setActionTextColor(Color.RED)
                                    .show()
                        }
                    }
                    true
                }
                .build()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE) {
//            val bitmap = getBitmapFromData(data!!)
//            imageView.setImageBitmap(bitmap)
        }
    }

    private fun getBitmapFromData(data: Intent): Bitmap? {
        var photo: Bitmap? = null
        val photoUri = data.data
        if (photoUri != null) {
            photo = BitmapFactory.decodeFile(photoUri.path)
        }
        if (photo == null) {
            val extra = data.extras
            if (extra != null) {
                photo = extra.get("data") as Bitmap
                val stream = ByteArrayOutputStream()
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            }
        }

        return photo
    }

}