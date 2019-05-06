package com.haixi.spacetime.Common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import static com.haixi.spacetime.Entity.Cookies.filePath;

public class FileOperation {
    Context context;
    public static byte[] bytes;
    private static int i = 0;
    public FileOperation(Context context){
        this.context = context;
    }

    public void downloadPicture(String accessKeyId, String secretKeyId, String securityToken,
                                final String fileName){
        bytes = new byte[1024 * 1024];
        i = 0;
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        OSSCredentialProvider credentialProvider =
                new OSSStsTokenCredentialProvider(accessKeyId, secretKeyId, securityToken);

        OSS oss = new OSSClient(context.getApplicationContext(), endpoint, credentialProvider);

        final GetObjectRequest get = new GetObjectRequest("spacetime-images", fileName);

        OSSAsyncTask task = oss.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest,
                GetObjectResult>() {
            @Override
            public void onSuccess(GetObjectRequest request, GetObjectResult result) {
                // 请求成功。
                Log.d("asyncGetObject", "DownloadSuccess");
                Log.d("Content-Length", "" + result.getContentLength());

                InputStream inputStream = result.getObjectContent();
                byte[] buffer = new byte[2048];
                int len;

                try {
                    while ((len = inputStream.read(buffer)) != -1) {
                        // 您可以在此处编写代码来处理下载的数据。
                        for (int j = 0; j < len; j++){
                            bytes[i] = buffer[j];
                            i++;
                        }
                    }
                    savePicture(fileName, bytes);
                    System.out.println("下载完毕**************************************");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            // GetObject请求成功，将返回GetObjectResult，其持有一个输入流的实例。返回的输入流，请自行处理。
            public void onFailure(GetObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常。
                if (clientExcepion != null) {
                    // 本地异常，如网络异常等。
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常。
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });

    }

    private void savePicture(String fileName, byte[] bytes){
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            if (!fileDir.mkdirs()) {
                return;
            }
        }

        File file = new File(filePath + fileName);
        RandomAccessFile raf = null;
        FileOutputStream out = null;
        try {
            //如果为追加则在原来的基础上继续写文件
            raf = new RandomAccessFile(file, "rw");
            raf.seek(file.length());
            raf.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isFileExist(String fileName){
        File file = new File(filePath + fileName);
        if (!file.exists()) {
            return false;
        }else return true;
    }
}
