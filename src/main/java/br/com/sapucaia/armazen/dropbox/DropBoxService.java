package br.com.sapucaia.armazen.dropbox;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.WriteMode;

@Service
public class DropBoxService {
	
	@Value("${dropbox.accessToken}")
    private String accessToken;

    
	@Bean
    private DbxClientV2 dbxClientV2() {
        return new DbxClientV2(
            DbxRequestConfig.newBuilder("sapucaians-storange").build(),
            accessToken
        );
    }
   
    public byte[] downloadFile(String filePath) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
        	dbxClientV2().files().download(filePath).download(outputStream);
            return outputStream.toByteArray();
        } catch (DbxException e) {
            throw new IOException("Error while downloading file from Dropbox.", e);
        }
    }

    public void uploadFile(String fileName, byte[] fileBytes) throws IOException {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes)) {
        	dbxClientV2().files().uploadBuilder("/" + fileName).withMode(WriteMode.ADD)
                    .uploadAndFinish(inputStream);
        } catch (DbxException e) {
            //throw new IOException("Error while uploading file to Dropbox.", e);
        	e.printStackTrace();
        }
    }

    public List<String> listFiles() {
        List<String> fileNames = new ArrayList<>();
        try {
            ListFolderResult result = dbxClientV2().files().listFolder("");
            while (true) {
                for (Metadata metadata : result.getEntries()) {
                    if (metadata instanceof FileMetadata) {
                        fileNames.add(metadata.getName());
                    }
                }
                if (!result.getHasMore()) {
                    break;
                }
                result = dbxClientV2().files().listFolderContinue(result.getCursor());
            }
        } catch (DbxException e) {
            e.printStackTrace();
        }
        return fileNames;
    }
}
