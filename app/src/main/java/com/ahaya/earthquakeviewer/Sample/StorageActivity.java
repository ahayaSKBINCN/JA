package com.ahaya.earthquakeviewer.Sample;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.DocumentsContract;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class StorageActivity extends AppCompatActivity {

    StorageManager storageManager;
    private static final int PICTURE_REQUEST_CODE = 7;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
//        StorageVolume volume = storageManager.getPrimaryStorageVolume();
        //volume.createAccessIntent(Environment.DIRECTORY_PICTURES has deprecated;
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);

        startActivityForResult(intent,PICTURE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICTURE_REQUEST_CODE){
            Uri documentTreeUri = data.getData();
            //使用返回的Uri访问目录中的文件；
            handleDocumentTreeUri(documentTreeUri);
        }
    }
    //使用DocumentContract类解析
    private void handleDocumentTreeUri(Uri documentTreeUri){
        Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
                documentTreeUri,
                DocumentsContract.getDocumentId(documentTreeUri)
        );
        try (
            Cursor children = getContentResolver().query(childrenUri,new String[] {
                    DocumentsContract.Document.COLUMN_DOCUMENT_ID,
                    DocumentsContract.Document.COLUMN_MIME_TYPE
            },null,null,null)
        ){
            if(children == null){
                return;
            }
            while (children.moveToNext()){
                String documentId = children.getString(0);
                String mimeType = children.getString(1);
                Uri childUri = DocumentsContract.buildChildDocumentsUriUsingTree(
                        documentTreeUri,
                        documentId
                );
                if(DocumentsContract.Document.MIME_TYPE_DIR.equals(mimeType)){
                    handleDocumentTreeUri(childUri);
                }else{
                    try(InputStream inputStream = getContentResolver().openInputStream(childUri)){
                        //TODO:读取文件
                    }catch (FileNotFoundException e ){
                        Log.e("FileNotFoundException","",e);
                    }catch(IOException e){
                        Log.e("IOException","",e);
                    }
                }
            }
        }
    }

    private void handleDocumentTreeUri(Uri documentTreeUri,
                                       //这个参数无用
                                       final String useDocumentFileClass){
        DocumentFile directory = DocumentFile.fromTreeUri(
                this,
                documentTreeUri
        );
        DocumentFile[] files = directory.listFiles();
        for(DocumentFile file: files){
            if(file.isDirectory()){
                handleDocumentTreeUri(file.getUri());
            }else {
                try (InputStream inputStream = getContentResolver().openInputStream(file.getUri())){
                //TODO:读取文件
                }catch (FileNotFoundException e ){
                    Log.e("FileNotFoundException","",e);
                }catch(IOException e){
                    Log.e("IOException","",e);
                }
            }
        }


    }
}
