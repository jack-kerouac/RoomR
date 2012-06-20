package controllers.rest;

import models.UploadFile;
import org.apache.commons.io.IOUtils;
import play.db.jpa.Blob;
import play.libs.MimeTypes;
import play.mvc.Controller;

import java.io.*;

public class PostImage extends Controller {
	public static void postImage(File image) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(new FileInputStream(image), baos);

		UploadFile uploadFile = new UploadFile();
		uploadFile.name = image.getName();

		String contentType = MimeTypes.getContentType(image.getName());
		uploadFile.data = new Blob();
		uploadFile.data.set(new ByteArrayInputStream(baos.toByteArray()), contentType);

		uploadFile.save();
	}
}
