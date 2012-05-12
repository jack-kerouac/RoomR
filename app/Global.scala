import play.api.GlobalSettings
import play.api.mvc.RequestHeader
import play.api.mvc.Results._

object Global extends GlobalSettings {
  override def onError(request: RequestHeader, ex: Throwable) = {
    InternalServerError(
      views.html.errors.serverError(ex))
  }
}