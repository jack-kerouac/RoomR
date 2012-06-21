package controllers.rest;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import controllers.rest.serialize.BriefUploadFileSerializer;
import controllers.rest.serialize.NameBasedExclusionStrategy;
import models.UploadFile;
import models.user.RoomrUser;
import play.db.jpa.Blob;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Router;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Images extends Controller {
	public static String getUrlFor(UploadFile uploadFile) {
		return Router.reverse("rest.Images.get", ImmutableMap.of("id", (Object) String.valueOf(uploadFile.id))).url;
	}


	public static void postImage(File image, String contentType) throws IOException {
		UploadFile uploadFile = new UploadFile();
		uploadFile.name = image.getName();

		uploadFile.data = new Blob();
		uploadFile.data.set(new FileInputStream(image), contentType);

		uploadFile.save();
	}

	public static void list() {
		List<UploadFile> images = UploadFile.all().fetch();

		Gson gson = RoomrGsonBuilder.builder().registerTypeAdapter(UploadFile.class, new BriefUploadFileSerializer()).create();
		renderJSON(gson.toJson(images));
	}

	public static void get(long id) {
		UploadFile image = UploadFile.findById(id);
		if (image == null) {
			notFound();
		}
		renderBinary(image.data.get(), image.name, image.data.type(), true);
	}

	public static void delete(long id) {
		UploadFile image = UploadFile.findById(id);
		if (image == null) {
			notFound();
		}
		image.delete();
		response.status = Http.StatusCode.NO_RESPONSE;
		ok();
	}
}
