package com.example.emall_ec.main.index.dailypic.pic

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.*
import android.widget.Toast
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.ImgUtils
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_show_image.*
import kotlinx.android.synthetic.main.show_image_fragment.view.*
import me.relex.photodraweeview.OnPhotoTapListener
import pub.devrel.easypermissions.EasyPermissions
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class ShowImageNewActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    lateinit var string_array: Array<String>

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build())
        setContentView(R.layout.delegate_show_image)
        string_array = arrayOf("0","1","2")
        EmallLogger.d( intent.getStringArrayListExtra("images"))
        string_array[0] = intent.getStringArrayListExtra("images").elementAt(0)
        if (intent.getStringExtra("picAmount").toInt() >= 2 && !intent.getStringArrayListExtra("images").elementAt(1).isEmpty())
            string_array[1] = intent.getStringArrayListExtra("images").elementAt(1)
        if (intent.getStringExtra("picAmount").toInt() == 3 &&!intent.getStringArrayListExtra("images").elementAt(2).isEmpty())
            string_array[2] = intent.getStringArrayListExtra("images").elementAt(2)

//        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        show_image_vp.adapter = mSectionsPagerAdapter
        show_image_vp.currentItem = intent.getStringExtra("index").toInt()

        save.setOnClickListener { requestPermission(PlaceholderFragment.urlString) }

    }


//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        val id = item.itemId
//
//        if (id == R.id.action_settings) {
//            return true
//        }
//
//        return super.onOptionsItemSelected(item)
//    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position, string_array)
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return intent.getStringExtra("picAmount").toInt()
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun requestPermission(url: String) {
        if (Build.VERSION.SDK_INT >= 23) {
            val mPermissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (EasyPermissions.hasPermissions(this, *mPermissionList)) {
                saveImage(url)
            } else {
                EasyPermissions.requestPermissions(this, "保存图片需要读取sd卡的权限", 10, *mPermissionList)
            }
        } else {
            saveImage(url)
        }
    }

    private fun saveImage(url: String) {
        val bitmap = getbitmap(url)
        val isSaveSuccess = ImgUtils.saveImageToGallery(this, bitmap)
        if (isSaveSuccess) {
            Toast.makeText(this, "保存图片成功", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "保存图片失败，请稍后重试", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getbitmap(imageUri: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val myFileUrl = URL(imageUri)
            val conn = myFileUrl
                    .openConnection() as HttpURLConnection
            conn.doInput = true
            conn.connect()
            val `is` = conn.inputStream
            bitmap = BitmapFactory.decodeStream(`is`)
            `is`.close()

        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            bitmap = null
        } catch (e: IOException) {
            e.printStackTrace()
            bitmap = null
        }

        return bitmap
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {

        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.show_image_fragment, container, false)

            println("url---->$url")
            rootView.image.setPhotoUri(Uri.parse(url[arguments.getInt(ARG_SECTION_NUMBER)]))
            urlString = url[arguments.getInt(ARG_SECTION_NUMBER)]
            rootView.image.onPhotoTapListener = OnPhotoTapListener { _, _, _ ->
                activity.finish()
            }

            return rootView
        }


        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"
            var url: Array<String> = arrayOf("0","1","2")
            var urlString = ""
            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int, array: Array<String>): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                url = array
                return fragment
            }
        }
    }
}
