package com.example.advancedpdfviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.advancedpdfviewer.databinding.ActivityMainBinding
import com.karumi.dexter.Dexter
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

class MainActivity : AppCompatActivity() {

    companion object{
        private const val PDF_PICK_CODE = 1000
    }
    private lateinit var mBinding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        runTimePermission()

        /*---var myAdapter = MainAdapter()
        mBinding.apply {
            rvItems.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            rvItems.adapter = myAdapter
        }---*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PDF_PICK_CODE && resultCode == Activity.RESULT_OK && data!=null){
            val selectedPdf: Uri? = data.data
            val intent = Intent(this@MainActivity, ViewDocument::class.java)
            intent.putExtra("ViewType","Storage")
            intent.putExtra("FileUri",selectedPdf.toString())
            startActivity(intent)
        }
    }

    private fun runTimePermission(){
        Dexter.withContext(this).withPermission(
            Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object : PermissionListener {
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                val getDocument = Intent(
                    Intent.ACTION_GET_CONTENT,
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                )
                getDocument.type = "application/pdf"
                getDocument.addCategory(Intent.CATEGORY_OPENABLE)
                startActivityForResult(Intent.createChooser(getDocument,"Select Pdf"), PDF_PICK_CODE)
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                Toast.makeText(
                    this@MainActivity,
                    "You have Denied permissions on this app",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?,
                p1: PermissionToken?
            ) {
                showDialogForPermissions()
            }


        }).onSameThread().check()
    }



    private fun showDialogForPermissions(){
        AlertDialog.Builder(this).setMessage("It looks like you have turned off permissions " +
                "required for this feature. It can be enabled under application settings")
            .setPositiveButton("GO TO SETTINGS"){
                    _,_ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }catch (e: ActivityNotFoundException){
                    e.printStackTrace()
                }
            }

            .setNegativeButton("Cancel"){
                    dialog,_->
                dialog.dismiss()
            }.show()
    }
}