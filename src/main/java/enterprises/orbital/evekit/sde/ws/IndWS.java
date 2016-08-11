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
import enterprises.orbital.evekit.sde.ind.IndActivity;
import enterprises.orbital.evekit.sde.ind.IndActivityMaterial;
import enterprises.orbital.evekit.sde.ind.IndActivityProbability;
import enterprises.orbital.evekit.sde.ind.IndActivityProduct;
import enterprises.orbital.evekit.sde.ind.IndActivitySkill;
import enterprises.orbital.evekit.sde.ind.IndBlueprint;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/ind")
@Consumes({
    "application/json"
})
@Produces({
    "application/json"
})
@Api(
    tags = {
        "Industry"
    },
    produces = "application/json",
    consumes = "application/json")
public class IndWS {

  @Path("/activity")
  @GET
  @ApiOperation(
      value = "Get industry activities")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested industry activities",
              response = IndActivity.class,
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
  public Response getActivities(
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
                                @QueryParam("activityID") @DefaultValue(
                                    value = "{ any: true }") @ApiParam(
                                        name = "activityID",
                                        required = false,
                                        defaultValue = "{ any: true }",
                                        value = "Activity ID selector") AttributeSelector activityID,
                                @QueryParam("time") @DefaultValue(
                                    value = "{ any: true }") @ApiParam(
                                        name = "time",
                                        required = false,
                                        defaultValue = "{ any: true }",
                                        value = "Time selector") AttributeSelector time) {
    ServiceUtil.sanitizeAttributeSelector(typeID, activityID, time);
    maxresults = Math.min(1000, maxresults);
    try {
      List<IndActivity> result = IndActivity.access(contid, maxresults, typeID, activityID, time);
      return ServiceUtil.finish(result, request);
    } catch (NumberFormatException e) {
      ServiceError errMsg = new ServiceError(Status.BAD_REQUEST.getStatusCode(), "An attribute selector contained an illegal value");
      return Response.status(Status.BAD_REQUEST).entity(errMsg).build();
    }
  }

  @Path("/activity_material")
  @GET
  @ApiOperation(
      value = "Get industry activity materials")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested industry activity materials",
              response = IndActivityMaterial.class,
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
  public Response getActivityMaterials(
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
                                       @QueryParam("activityID") @DefaultValue(
                                           value = "{ any: true }") @ApiParam(
                                               name = "activityID",
                                               required = false,
                                               defaultValue = "{ any: true }",
                                               value = "Activity ID selector") AttributeSelector activityID,
                                       @QueryParam("materialTypeID") @DefaultValue(
                                           value = "{ any: true }") @ApiParam(
                                               name = "materialTypeID",
                                               required = false,
                                               defaultValue = "{ any: true }",
                                               value = "Material type ID selector") AttributeSelector materialTypeID,
                                       @QueryParam("quantity") @DefaultValue(
                                           value = "{ any: true }") @ApiParam(
                                               name = "quantity",
                                               required = false,
                                               defaultValue = "{ any: true }",
                                               value = "Quantity selector") AttributeSelector quantity) {
    ServiceUtil.sanitizeAttributeSelector(typeID, activityID, materialTypeID, quantity);
    maxresults = Math.min(1000, maxresults);
    try {
      List<IndActivityMaterial> result = IndActivityMaterial.access(contid, maxresults, typeID, activityID, materialTypeID, quantity);
      return ServiceUtil.finish(result, request);
    } catch (NumberFormatException e) {
      ServiceError errMsg = new ServiceError(Status.BAD_REQUEST.getStatusCode(), "An attribute selector contained an illegal value");
      return Response.status(Status.BAD_REQUEST).entity(errMsg).build();
    }
  }

  @Path("/activity_probability")
  @GET
  @ApiOperation(
      value = "Get industry activity probabilities")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested industry activity probabilities",
              response = IndActivityProbability.class,
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
  public Response getActivityProbabilities(
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
                                           @QueryParam("activityID") @DefaultValue(
                                               value = "{ any: true }") @ApiParam(
                                                   name = "activityID",
                                                   required = false,
                                                   defaultValue = "{ any: true }",
                                                   value = "Activity ID selector") AttributeSelector activityID,
                                           @QueryParam("productTypeID") @DefaultValue(
                                               value = "{ any: true }") @ApiParam(
                                                   name = "productTypeID",
                                                   required = false,
                                                   defaultValue = "{ any: true }",
                                                   value = "Product type ID selector") AttributeSelector productTypeID,
                                           @QueryParam("probability") @DefaultValue(
                                               value = "{ any: true }") @ApiParam(
                                                   name = "probability",
                                                   required = false,
                                                   defaultValue = "{ any: true }",
                                                   value = "Probability selector") AttributeSelector probability) {
    ServiceUtil.sanitizeAttributeSelector(typeID, activityID, productTypeID, probability);
    maxresults = Math.min(1000, maxresults);
    try {
      List<IndActivityProbability> result = IndActivityProbability.access(contid, maxresults, typeID, activityID, productTypeID, probability);
      return ServiceUtil.finish(result, request);
    } catch (NumberFormatException e) {
      ServiceError errMsg = new ServiceError(Status.BAD_REQUEST.getStatusCode(), "An attribute selector contained an illegal value");
      return Response.status(Status.BAD_REQUEST).entity(errMsg).build();
    }
  }

  @Path("/activity_product")
  @GET
  @ApiOperation(
      value = "Get industry activity products")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested industry activity products",
              response = IndActivityProduct.class,
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
  public Response getActivityProducts(
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
                                      @QueryParam("activityID") @DefaultValue(
                                          value = "{ any: true }") @ApiParam(
                                              name = "activityID",
                                              required = false,
                                              defaultValue = "{ any: true }",
                                              value = "Activity ID selector") AttributeSelector activityID,
                                      @QueryParam("productTypeID") @DefaultValue(
                                          value = "{ any: true }") @ApiParam(
                                              name = "productTypeID",
                                              required = false,
                                              defaultValue = "{ any: true }",
                                              value = "Product type ID selector") AttributeSelector productTypeID,
                                      @QueryParam("quantity") @DefaultValue(
                                          value = "{ any: true }") @ApiParam(
                                              name = "quantity",
                                              required = false,
                                              defaultValue = "{ any: true }",
                                              value = "Quantity selector") AttributeSelector quantity) {
    ServiceUtil.sanitizeAttributeSelector(typeID, activityID, productTypeID, quantity);
    maxresults = Math.min(1000, maxresults);
    try {
      List<IndActivityProduct> result = IndActivityProduct.access(contid, maxresults, typeID, activityID, productTypeID, quantity);
      return ServiceUtil.finish(result, request);
    } catch (NumberFormatException e) {
      ServiceError errMsg = new ServiceError(Status.BAD_REQUEST.getStatusCode(), "An attribute selector contained an illegal value");
      return Response.status(Status.BAD_REQUEST).entity(errMsg).build();
    }
  }

  @Path("/activity_skill")
  @GET
  @ApiOperation(
      value = "Get industry activity skills")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested industry activity skills",
              response = IndActivitySkill.class,
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
  public Response getActivitySkills(
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
                                    @QueryParam("activityID") @DefaultValue(
                                        value = "{ any: true }") @ApiParam(
                                            name = "activityID",
                                            required = false,
                                            defaultValue = "{ any: true }",
                                            value = "Activity ID selector") AttributeSelector activityID,
                                    @QueryParam("skillID") @DefaultValue(
                                        value = "{ any: true }") @ApiParam(
                                            name = "skillID",
                                            required = false,
                                            defaultValue = "{ any: true }",
                                            value = "Skill ID selector") AttributeSelector skillID,
                                    @QueryParam("level") @DefaultValue(
                                        value = "{ any: true }") @ApiParam(
                                            name = "level",
                                            required = false,
                                            defaultValue = "{ any: true }",
                                            value = "Level selector") AttributeSelector level) {
    ServiceUtil.sanitizeAttributeSelector(typeID, activityID, skillID, level);
    maxresults = Math.min(1000, maxresults);
    try {
      List<IndActivitySkill> result = IndActivitySkill.access(contid, maxresults, typeID, activityID, skillID, level);
      return ServiceUtil.finish(result, request);
    } catch (NumberFormatException e) {
      ServiceError errMsg = new ServiceError(Status.BAD_REQUEST.getStatusCode(), "An attribute selector contained an illegal value");
      return Response.status(Status.BAD_REQUEST).entity(errMsg).build();
    }
  }

  @Path("/blueprint")
  @GET
  @ApiOperation(
      value = "Get blueprints")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested blueprints",
              response = IndBlueprint.class,
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
  public Response getBlueprintTypes(
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
                                    @QueryParam("maxProductionLimit") @DefaultValue(
                                        value = "{ any: true }") @ApiParam(
                                            name = "maxProductionLimit",
                                            required = false,
                                            defaultValue = "{ any: true }",
                                            value = "Max production limit selector") AttributeSelector maxProductionLimit) {
    ServiceUtil.sanitizeAttributeSelector(typeID, maxProductionLimit);
    maxresults = Math.min(1000, maxresults);
    try {
      List<IndBlueprint> result = IndBlueprint.access(contid, maxresults, typeID, maxProductionLimit);
      return ServiceUtil.finish(result, request);
    } catch (NumberFormatException e) {
      ServiceError errMsg = new ServiceError(Status.BAD_REQUEST.getStatusCode(), "An attribute selector contained an illegal value");
      return Response.status(Status.BAD_REQUEST).entity(errMsg).build();
    }
  }

}
