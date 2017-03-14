package com.stevengantz.json;

/**
 * Jackson element generated by http://www.jsonschema2pojo.org/
 * Created by Steven Gantz on 2/26/2017.
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Header", "HeaderSubtitle", "ExpandedText", "Image", "Buttons" })
public class Card {

	@JsonProperty("Header")
	private String header;
	@JsonProperty("HeaderSubtitle")
	private String headerSubtitle;
	@JsonProperty("ExpandedText")
	private String expandedText;
	@JsonProperty("Image")
	private String image;
	@JsonProperty("Buttons")
	private List<Button> buttons = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("Header")
	public String getHeader() {
		return header;
	}

	@JsonProperty("Header")
	public void setHeader(String header) {
		this.header = header;
	}

	public Card withHeader(String header) {
		this.header = header;
		return this;
	}

	@JsonProperty("HeaderSubtitle")
	public String getHeaderSubtitle() {
		return headerSubtitle;
	}

	@JsonProperty("HeaderSubtitle")
	public void setHeaderSubtitle(String headerSubtitle) {
		this.headerSubtitle = headerSubtitle;
	}

	public Card withHeaderSubtitle(String headerSubtitle) {
		this.headerSubtitle = headerSubtitle;
		return this;
	}

	@JsonProperty("ExpandedText")
	public String getExpandedText() {
		return expandedText;
	}

	@JsonProperty("ExpandedText")
	public void setExpandedText(String expandedText) {
		this.expandedText = expandedText;
	}

	public Card withExpandedText(String expandedText) {
		this.expandedText = expandedText;
		return this;
	}

	@JsonProperty("Image")
	public String getImage() {
		return image;
	}

	@JsonProperty("Image")
	public void setImage(String image) {
		this.image = image;
	}

	public Card withImage(String image) {
		this.image = image;
		return this;
	}

	@JsonProperty("Buttons")
	public List<Button> getButtons() {
		return buttons;
	}

	@JsonProperty("Buttons")
	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}

	public Card withButtons(List<Button> buttons) {
		this.buttons = buttons;
		return this;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public Card withAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
		return this;
	}

}