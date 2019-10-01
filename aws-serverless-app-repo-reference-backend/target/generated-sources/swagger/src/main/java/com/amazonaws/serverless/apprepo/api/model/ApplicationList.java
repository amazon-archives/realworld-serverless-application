package com.amazonaws.serverless.apprepo.api.model;

import com.amazonaws.serverless.apprepo.api.model.ApplicationSummary;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ApplicationList   {
  
  private @Valid List<ApplicationSummary> applications = new ArrayList<ApplicationSummary>();
  private @Valid String nextToken = null;

  /**
   **/
  public ApplicationList applications(List<ApplicationSummary> applications) {
    this.applications = applications;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("applications")
  public List<ApplicationSummary> getApplications() {
    return applications;
  }
  public void setApplications(List<ApplicationSummary> applications) {
    this.applications = applications;
  }

  /**
   **/
  public ApplicationList nextToken(String nextToken) {
    this.nextToken = nextToken;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("nextToken")
  public String getNextToken() {
    return nextToken;
  }
  public void setNextToken(String nextToken) {
    this.nextToken = nextToken;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApplicationList applicationList = (ApplicationList) o;
    return Objects.equals(applications, applicationList.applications) &&
        Objects.equals(nextToken, applicationList.nextToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applications, nextToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApplicationList {\n");
    
    sb.append("    applications: ").append(toIndentedString(applications)).append("\n");
    sb.append("    nextToken: ").append(toIndentedString(nextToken)).append("\n");
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

