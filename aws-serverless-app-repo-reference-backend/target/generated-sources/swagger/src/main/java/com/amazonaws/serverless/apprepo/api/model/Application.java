package com.amazonaws.serverless.apprepo.api.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Application   {
  
  private @Valid String applicationId = null;
  private @Valid String description = null;
  private @Valid String author = null;
  private @Valid String creationTime = null;
  private @Valid String homePageUrl = null;

  /**
   **/
  public Application applicationId(String applicationId) {
    this.applicationId = applicationId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("applicationId")
  @NotNull
  public String getApplicationId() {
    return applicationId;
  }
  public void setApplicationId(String applicationId) {
    this.applicationId = applicationId;
  }

  /**
   **/
  public Application description(String description) {
    this.description = description;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("description")
  @NotNull
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   **/
  public Application author(String author) {
    this.author = author;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("author")
  @NotNull
  public String getAuthor() {
    return author;
  }
  public void setAuthor(String author) {
    this.author = author;
  }

  /**
   **/
  public Application creationTime(String creationTime) {
    this.creationTime = creationTime;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("creationTime")
  public String getCreationTime() {
    return creationTime;
  }
  public void setCreationTime(String creationTime) {
    this.creationTime = creationTime;
  }

  /**
   **/
  public Application homePageUrl(String homePageUrl) {
    this.homePageUrl = homePageUrl;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("homePageUrl")
  public String getHomePageUrl() {
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
    Application application = (Application) o;
    return Objects.equals(applicationId, application.applicationId) &&
        Objects.equals(description, application.description) &&
        Objects.equals(author, application.author) &&
        Objects.equals(creationTime, application.creationTime) &&
        Objects.equals(homePageUrl, application.homePageUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicationId, description, author, creationTime, homePageUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Application {\n");
    
    sb.append("    applicationId: ").append(toIndentedString(applicationId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    creationTime: ").append(toIndentedString(creationTime)).append("\n");
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

