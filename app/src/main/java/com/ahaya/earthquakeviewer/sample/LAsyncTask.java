package com.ahaya.earthquakeviewer.sample;

import android.os.AsyncTask;

public class LAsyncTask extends AsyncTask<String,Integer,String> {
    @Override
    protected String doInBackground(String... strings) {
        //移动到后台线程
        String result = "";
        int myProgress = 0;
        int inputLength = strings[0].length();
        //执行后台任务处理，更新myProgress

        for (int i = 1; i<= inputLength; i++){
            myProgress = i;
            result = result+strings[0].charAt(inputLength-1);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                publishProgress(myProgress);

        }
        return result;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // 同步ui线程
        //更新进度条、通知或其他ui元素
    }

    @Override
    protected void onPostExecute(String s) {
        //同步ui线程
        //通过ui更新、弹窗或通过通知报告执行结果；
    }
}
