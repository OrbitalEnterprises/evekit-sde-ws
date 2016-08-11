package enterprises.orbital.evekit.sde.ws;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import enterprises.orbital.evekit.sde.AttributeSelector;
import enterprises.orbital.evekit.sde.crt.CrtCertificate;
import enterprises.orbital.evekit.sde.crt.CrtMastery;
import enterprises.orbital.evekit.sde.crt.CrtSkill;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/crt")
@Consumes({
    "application/json"
})
@Produces({
    "application/json"
})
@Api(
    tags = {
        "Certificates"
    },
    produces = "application/json",
    consumes = "application/json")
public class CrtWS {

  @Path("/certificate")
  @GET
  @ApiOperation(
      value = "Get certificates")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested certificates",
              response = CrtCertificate.class,
              responseContainer = "array"),
          @ApiResponse(
              code = 400,
              message = "invalid attribute selector",
              response = ServiceError.class),
          @ApiResponse(
              code = 500,
              message = "internal service error",
              response = ServiceError.class),
      })
  public Response getCertificates(
                                  @Context HttpServletRequest request,
                                  @QueryParam("contid") @DefaultValue("-1") @ApiParam(
                                      name = "contid",
                                      required = false,
                                      defaultValue = "-1",
                                      value = "Continuation ID for paged results") int contid,
                                  @QueryParam("maxresults") @DefaultValue("1000") @ApiParam(
                                      name = "maxresults",
                                      required = false,
                                      defaultValue = "1000",
                                      value = "Maximum number of results to retrieve") int maxresults,
                                  @QueryParam("certID") @DefaultValue(
                                      value = "{ any: true }") @ApiParam(
                                          name = "certID",
                                          required = false,
                                          defaultValue = "{ any: true }",
                                          value = "Certificate ID selector") AttributeSelector certID,
                                  @QueryParam("description") @DefaultValue(
                                      value = "{ any: true }") @ApiParam(
                                          name = "description",
                                          required = false,
                                          defaultValue = "{ any: true }",
                                          value = "Description selector") AttributeSelector description,
                                  @QueryParam("groupID") @DefaultValue(
                                      value = "{ any: true }") @ApiParam(
                                          name = "groupID",
                                          required = false,
                                          defaultValue = "{ any: true }",
                                          value = "Group ID selector") AttributeSelector groupID,
                                  @QueryParam("name") @DefaultValue(
                                      value = "{ any: true }") @ApiParam(
                                          name = "name",
                                          required = false,
                                          defaultValue = "{ any: true }",
                                          value = "Name selector") AttributeSelector name) {
    ServiceUtil.sanitizeAttributeSelector(certID, description, groupID, name);
    maxresults = Math.min(1000, maxresults);
    try {
      List<CrtCertificate> result = CrtCertificate.access(contid, maxresults, certID, description, groupID, name);
      return ServiceUtil.finish(result, request);
    } catch (NumberFormatException e) {
      ServiceError errMsg = new ServiceError(Status.BAD_REQUEST.getStatusCode(), "An attribute selector contained an illegal value");
      return Response.status(Status.BAD_REQUEST).entity(errMsg).build();
    }
  }

  @Path("/mastery")
  @GET
  @ApiOperation(
      value = "Get certificate masteries")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested certificate masteries",
              response = CrtMastery.class,
              responseContainer = "array"),
          @ApiResponse(
              code = 400,
              message = "invalid attribute selector",
              response = ServiceError.class),
          @ApiResponse(
              code = 500,
              message = "internal service error",
              response = ServiceError.class),
      })
  public Response getCertificateMasteries(
                                          @Context HttpServletRequest request,
                                          @QueryParam("contid") @DefaultValue("-1") @ApiParam(
                                              name = "contid",
                                              required = false,
                                              defaultValue = "-1",
                                              value = "Continuation ID for paged results") int contid,
                                          @QueryParam("maxresults") @DefaultValue("1000") @ApiParam(
                                              name = "maxresults",
                                              required = false,
                                              defaultValue = "1000",
                                              value = "Maximum number of results to retrieve") int maxresults,
                                          @QueryParam("typeID") @DefaultValue(
                                              value = "{ any: true }") @ApiParam(
                                                  name = "typeID",
                                                  required = false,
                                                  defaultValue = "{ any: true }",
                                                  value = "Type ID selector") AttributeSelector typeID,
                                          @QueryParam("masteryLevel") @DefaultValue(
                                              value = "{ any: true }") @ApiParam(
                                                  name = "masteryLevel",
                                                  required = false,
                                                  defaultValue = "{ any: true }",
                                                  value = "Mastery level selector") AttributeSelector masteryLevel,
                                          @QueryParam("certID") @DefaultValue(
                                              value = "{ any: true }") @ApiParam(
                                                  name = "certID",
                                                  required = false,
                                                  defaultValue = "{ any: true }",
                                                  value = "Certificate ID selector") AttributeSelector certID) {
    ServiceUtil.sanitizeAttributeSelector(typeID, masteryLevel, certID);
    maxresults = Math.min(1000, maxresults);
    try {
      List<CrtMastery> result = CrtMastery.access(contid, maxresults, typeID, masteryLevel, certID);
      return ServiceUtil.finish(result, request);
    } catch (NumberFormatException e) {
      ServiceError errMsg = new ServiceError(Status.BAD_REQUEST.getStatusCode(), "An attribute selector contained an illegal value");
      return Response.status(Status.BAD_REQUEST).entity(errMsg).build();
    }
  }

  @Path("/skill")
  @GET
  @ApiOperation(
      value = "Get certificate skills")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested certificate skills",
              response = CrtSkill.class,
              responseContainer = "array"),
          @ApiResponse(
              code = 400,
              message = "invalid attribute selector",
              response = ServiceError.class),
          @ApiResponse(
              code = 500,
              message = "internal service error",
              response = ServiceError.class),
      })
  public Response getCertificateSkills(
                                       @Context HttpServletRequest request,
                                       @QueryParam("contid") @DefaultValue("-1") @ApiParam(
                                           name = "contid",
                                           required = false,
                                           defaultValue = "-1",
                                           value = "Continuation ID for paged results") int contid,
                                       @QueryParam("maxresults") @DefaultValue("1000") @ApiParam(
                                           name = "maxresults",
                                           required = false,
                                           defaultValue = "1000",
                                           value = "Maximum number of results to retrieve") int maxresults,
                                       @QueryParam("certID") @DefaultValue(
                                           value = "{ any: true }") @ApiParam(
                                               name = "certID",
                                               required = false,
                                               defaultValue = "{ any: true }",
                                               value = "Certificated ID selector") AttributeSelector certID,
                                       @QueryParam("skillID") @DefaultValue(
                                           value = "{ any: true }") @ApiParam(
                                               name = "skillID",
                                               required = false,
                                               defaultValue = "{ any: true }",
                                               value = "Skill ID selector") AttributeSelector skillID,
                                       @QueryParam("certLevelInt") @DefaultValue(
                                           value = "{ any: true }") @ApiParam(
                                               name = "certLevelInt",
                                               required = false,
                                               defaultValue = "{ any: true }",
                                               value = "Certificate level integer selector") AttributeSelector certLevelInt,
                                       @QueryParam("skillLevel") @DefaultValue(
                                           value = "{ any: true }") @ApiParam(
                                               name = "skillLevel",
                                               required = false,
                                               defaultValue = "{ any: true }",
                                               value = "Skill level selector") AttributeSelector skillLevel,
                                       @QueryParam("certLevelText") @DefaultValue(
                                           value = "{ any: true }") @ApiParam(
                                               name = "certLevelText",
                                               required = false,
                                               defaultValue = "{ any: true }",
                                               value = "Certificate level text selector") AttributeSelector certLevelText) {
    ServiceUtil.sanitizeAttributeSelector(certID, skillID, certLevelInt, skillLevel, certLevelText);
    maxresults = Math.min(1000, maxresults);
    try {
      List<CrtSkill> result = CrtSkill.access(contid, maxresults, certID, skillID, certLevelInt, skillLevel, certLevelText);
      return ServiceUtil.finish(result, request);
    } catch (NumberFormatException e) {
      ServiceError errMsg = new ServiceError(Status.BAD_REQUEST.getStatusCode(), "An attribute selector contained an illegal value");
      return Response.status(Status.BAD_REQUEST).entity(errMsg).build();
    }
  }

}
