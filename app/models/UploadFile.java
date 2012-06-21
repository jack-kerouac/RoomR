package models;

import play.db.jpa.Blob;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class UploadFile extends Model {
	public String name;
	public Blob data;
}
