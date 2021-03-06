package tfip.ibf.BookApp.Model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Book {
    
    private String title ;
    private String works_id;
    private String resource;
    private String description;
    private String excerpt; 
    private boolean cached; 

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExcerpt() {
		return this.excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}


    public String getResource() {
        return this.resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWorks_id() {
        return this.works_id;
    }

    public void setWorks_id(String works_id) {
        this.works_id = works_id;
    }

    public static Book create(JsonObject o) {
        final Book b = new Book();
        b.setTitle(o.getString("title"));
        b.setWorks_id(o.getString("key").split("/")[2]);
        b.setResource("/book/"+o.getString("key").split("/")[2]);
        return b;
    }

    public static Book create(String jsonString) {
        try (InputStream is = new ByteArrayInputStream(jsonString.getBytes())) {
            final JsonReader reader = Json.createReader(is);
            return create(reader.readObject());
        } catch (Exception ex) { }

        // Need to handle error
        return new Book();
    }

    @Override
    public String toString() {
        return this.toJson().toString();
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("title", title)
            .add("works_id", works_id)
            .build();
    }

}
