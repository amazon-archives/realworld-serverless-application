package com.amazonaws.serverless.apprepo.api.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class CreateApplicationInput   {
  
  private @Valid String applicationId = null;
  private @Valid String description = null;
  private @Valid String author = null;
  private @Valid String homePageUrl = null;

  /**
   **/
  public CreateApplicationInput applicationId(String applicationId) {
    this.applicationId = applicationId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("applicationId")
  @NotNull
 @Pattern(regexp="^[a-zA-Z0-9\\-]{3,128}$")  public String getApplicationId() {
    return applicationId;
  }
  public void setApplicationId(String applicationId) {
    this.applicationId = applicationId;
  }

  /**
   **/
  public CreateApplicationInput description(String description) {
    this.description = description;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("description")
  @NotNull
 @Pattern(regexp="^[a-zA-Z0-9\\-]{3,128}$")  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   **/
  public CreateApplicationInput author(String author) {
    this.author = author;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("author")
  @NotNull
 @Pattern(regexp="^[a-zA-Z0-9\\-]{1,128}$")  public String getAuthor() {
    return author;
  }
  public void setAuthor(String author) {
    this.author = author;
  }

  /**
   **/
  public CreateApplicationInput homePageUrl(String homePageUrl) {
    this.homePageUrl = homePageUrl;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("homePageUrl")
 @Pattern(regexp="^(http://www\\.|https://www\\.|http://|https://)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(/.*)?$")  public String getHomePageUrl() {
    return homePageUrl;
  }
  public void setHomePageUrl(String homePageUrl) {
    this.homePageUrl = homePageUrl;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateApplicationInput createApplicationInput = (CreateApplicationInput) o;
    return Objects.equals(applicationId, createApplicationInput.applicationId) &&
        Objects.equals(description, createApplicationInput.description) &&
        Objects.equals(author, createApplicationInput.author) &&
        Objects.equals(homePageUrl, createApplicationInput.homePageUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicationId, description, author, homePageUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateApplicationInput {\n");
    
    sb.append("    applicationId: ").append(toIndentedString(applicationId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    homePageUrl: ").append(toIndentedString(homePageUrl)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

