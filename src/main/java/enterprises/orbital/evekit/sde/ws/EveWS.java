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
import enterprises.orbital.evekit.sde.eve.EveGraphic;
import enterprises.orbital.evekit.sde.eve.EveIcon;
import enterprises.orbital.evekit.sde.eve.EveUnit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/eve")
@Consumes({
    "application/json"
})
@Produces({
    "application/json"
})
@Api(
    tags = {
        "EVE"
    },
    produces = "application/json",
    consumes = "application/json")
public class EveWS {

  @Path("/graphic")
  @GET
  @ApiOperation(
      value = "Get graphics")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested graphics",
              response = EveGraphic.class,
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
  public Response getGraphics(
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
                              @QueryParam("graphicID") @DefaultValue(
                                  value = "{ any: true }") @ApiParam(
                                      name = "graphicID",
                                      required = false,
                                      defaultValue = "{ any: true }",
                                      value = "Graphic ID selector") AttributeSelector graphicID,
                              @QueryParam("sofFactionName") @DefaultValue(
                                  value = "{ any: true }") @ApiParam(
                                      name = "sofFactionName",
                                      required = false,
                                      defaultValue = "{ any: true }",
                                      value = "Faction name selector") AttributeSelector sofFactionName,
                              @QueryParam("graphicFile") @DefaultValue(
                                  value = "{ any: true }") @ApiParam(
                                      name = "graphicFile",
                                      required = false,
                                      defaultValue = "{ any: true }",
                                      value = "Graphic file selector") AttributeSelector graphicFile,
                              @QueryParam("sofHullName") @DefaultValue(
                                  value = "{ any: true }") @ApiParam(
                                      name = "sofHullName",
                                      required = false,
                                      defaultValue = "{ any: true }",
                                      value = "Hull name selector") AttributeSelector sofHullName,
                              @QueryParam("sofRaceName") @DefaultValue(
                                  value = "{ any: true }") @ApiParam(
                                      name = "sofRaceName",
                                      required = false,
                                      defaultValue = "{ any: true }",
                                      value = "Race name selector") AttributeSelector sofRaceName,
                              @QueryParam("description") @DefaultValue(
                                  value = "{ any: true }") @ApiParam(
                                      name = "description",
                                      required = false,
                                      defaultValue = "{ any: true }",
                                      value = "Description selector") AttributeSelector description) {
    ServiceUtil.sanitizeAttributeSelector(graphicID, sofFactionName, graphicFile, sofHullName, sofRaceName, description);
    maxresults = Math.min(1000, maxresults);
    try {
      List<EveGraphic> result = EveGraphic.access(contid, maxresults, graphicID, sofFactionName, graphicFile, sofHullName, sofRaceName, description);
      return ServiceUtil.finish(result, request);
    } catch (NumberFormatException e) {
      ServiceError errMsg = new ServiceError(Status.BAD_REQUEST.getStatusCode(), "An attribute selector contained an illegal value");
      return Response.status(Status.BAD_REQUEST).entity(errMsg).build();
    }
  }

  @Path("/icon")
  @GET
  @ApiOperation(
      value = "Get icons")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested icons",
              response = EveIcon.class,
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
  public Response getIcons(
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
                           @QueryParam("iconID") @DefaultValue(
                               value = "{ any: true }") @ApiParam(
                                   name = "iconID",
                                   required = false,
                                   defaultValue = "{ any: true }",
                                   value = "Icon ID selector") AttributeSelector iconID,
                           @QueryParam("description") @DefaultValue(
                               value = "{ any: true }") @ApiParam(
                                   name = "description",
                                   required = false,
                                   defaultValue = "{ any: true }",
                                   value = "Description text selector") AttributeSelector description,
                           @QueryParam("iconFile") @DefaultValue(
                               value = "{ any: true }") @ApiParam(
                                   name = "iconFile",
                                   required = false,
                                   defaultValue = "{ any: true }",
                                   value = "Icon file selector") AttributeSelector iconFile) {
    ServiceUtil.sanitizeAttributeSelector(iconID, description, iconFile);
    maxresults = Math.min(1000, maxresults);
    try {
      List<EveIcon> result = EveIcon.access(contid, maxresults, iconID, description, iconFile);
      return ServiceUtil.finish(result, request);
    } catch (NumberFormatException e) {
      ServiceError errMsg = new ServiceError(Status.BAD_REQUEST.getStatusCode(), "An attribute selector contained an illegal value");
      return Response.status(Status.BAD_REQUEST).entity(errMsg).build();
    }
  }

  @Path("/unit")
  @GET
  @ApiOperation(
      value = "Get units")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested units",
              response = EveUnit.class,
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
  public Response getUnits(
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
                           @QueryParam("unitID") @DefaultValue(
                               value = "{ any: true }") @ApiParam(
                                   name = "unitID",
                                   required = false,
                                   defaultValue = "{ any: true }",
                                   value = "Unit ID selector") AttributeSelector unitID,
                           @QueryParam("description") @DefaultValue(
                               value = "{ any: true }") @ApiParam(
                                   name = "description",
                                   required = false,
                                   defaultValue = "{ any: true }",
                                   value = "Description text selector") AttributeSelector description,
                           @QueryParam("displayName") @DefaultValue(
                               value = "{ any: true }") @ApiParam(
                                   name = "displayName",
                                   required = false,
                                   defaultValue = "{ any: true }",
                                   value = "Display name selector") AttributeSelector displayName,
                           @QueryParam("unitName") @DefaultValue(
                               value = "{ any: true }") @ApiParam(
                                   name = "unitName",
                                   required = false,
                                   defaultValue = "{ any: true }",
                                   value = "Unit name selector") AttributeSelector unitName) {
    ServiceUtil.sanitizeAttributeSelector(unitID, description, displayName, unitName);
    maxresults = Math.min(1000, maxresults);
    try {
      List<EveUnit> result = EveUnit.access(contid, maxresults, unitID, description, displayName, unitName);
      return ServiceUtil.finish(result, request);
    } catch (NumberFormatException e) {
      ServiceError errMsg = new ServiceError(Status.BAD_REQUEST.getStatusCode(), "An attribute selector contained an illegal value");
      return Response.status(Status.BAD_REQUEST).entity(errMsg).build();
    }
  }

}
