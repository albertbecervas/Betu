package com.lbcompany.betu

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView

import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader

/**
 * Created by Albert on 18/09/2017.
 */

class fasf {
    private val headerResult: AccountHeader
    private val profileDrawerItem: ProfileDrawerItem

    init {
        val item1 = PrimaryDrawerItem().withIdentifier(1).withName("My account")
        item1.withIcon(R.drawable.avatar)
        val item3 = SecondaryDrawerItem().withIdentifier(3).withName("Logout")
        item3.withIcon(R.drawable.avatar)

        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {
            override fun set(imageView: ImageView?, uri: Uri?, placeholder: Drawable?) {
                /*if (mPrefs.getImageUrl() != null) {
                    Glide.with(imageView.getContext()).load(mPrefs.getImageUrl()).into(imageView);
                }*/
            }

            override fun cancel(imageView: ImageView?) {
                super.cancel(imageView)
            }

            override fun placeholder(ctx: Context, tag: String?): Drawable {
                return super.placeholder(ctx, tag)
            }
        })


        profileDrawerItem = ProfileDrawerItem().withName("albert").withEmail("vgsdbf").withIcon(ALBERT_URL).withIdentifier(4)

        // Create the AccountHeader
        headerResult = AccountHeaderBuilder()
                //                .withActivity()
                .withHeaderBackground(R.drawable.common_google_signin_btn_icon_dark_normal_background)
                .withOnAccountHeaderProfileImageListener(object : AccountHeader.OnAccountHeaderProfileImageListener {
                    override fun onProfileImageClick(view: View, profile: IProfile<*>, current: Boolean): Boolean {
                        if (profile.identifier == 4L) {
                            val uri: String? = null
                            /* switch (mPrefs.getUserEmail()) {
                                case "a@o.com":
                                    uri = ALBERT_URL;
                                    break;
                                case "x@o.com":
                                    uri = XAVI_URL;
                                    break;
                                case "j@o.com":
                                    uri = JORGE_URL;
                                    break;
                                case "z@o.com":
                                    uri = OSCAR_URL;
                                    break;
                                case "b@o.com":
                                    uri = BERTUS_URL;
                                    break;
                            }*/
                            if (uri != null) {
                                //                                mFirebaseProfile.setImage(String.valueOf(uri));
                                //                                mPrefs.setImageUrl(String.valueOf(uri));
                                //                                Glide.with(view.getContext()).load(uri).into((ImageView) view);
                            }
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
                //                .withActivity(this)
                .withAccountHeader(headerResult)
                //                .withToolbar(ge)
                .addDrawerItems(
                        item1,
                        DividerDrawerItem(),
                        item3
                )
                .withOnDrawerItemClickListener { view, position, drawerItem ->
                    // do something with the clicked item :D

                    when (drawerItem.identifier.toInt()) {
                        1 -> {
                        }
                    }//                                startActivityForResult(new Intent(ChatsList.this, UserProfile.class), 10);
                    //                                setOnLogUserOutClicked();
                    //                                mFirebaseProfile.onLogOut();
                    //                                mPrefs.deletePreferences();
                    //                                startActivity(new Intent(ChatsList.this, MainActivity.class));
                    //                                finish();


                    true
                }
                .build()
    }

    companion object {

        private val ALBERT_URL = ""
    }
}
