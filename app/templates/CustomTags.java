package templates;

import groovy.lang.Closure;

import java.io.PrintWriter;
import java.util.Map;

import play.Play;
import play.exceptions.TagInternalException;
import play.exceptions.TemplateExecutionException;
import play.modules.guice.GuicePlugin;
import play.mvc.Router.ActionDefinition;
import play.templates.FastTags;
import play.templates.GroovyTemplate.ExecutableTemplate;

import com.google.appengine.api.users.UserService;

public class CustomTags extends FastTags {
	private static UserService getUserService() {
		GuicePlugin plugin = (GuicePlugin) Play.pluginCollection.getPluginInstance(GuicePlugin.class);
		return plugin.getBeanOfType(UserService.class);
	}

	public static void _loginUrl(Map<?, ?> args, Closure body, PrintWriter out, ExecutableTemplate template,
			int fromLine) {
		final Object continueParam = args.get("continue");
		String continueUrl = null;

		if (continueParam instanceof ActionDefinition) {
			continueUrl = ((ActionDefinition) continueParam).url;
		} else if (continueParam instanceof String) {
			continueUrl = continueParam.toString();
		} else {
			throw new TemplateExecutionException(template.template, fromLine,
					"Wrong parameter type, try #{loginUrl continue:@Application.index() /}", new TagInternalException(
							"Wrong parameter type"));
		}

		out.print(getUserService().createLoginURL(controllers.Registration.createRegistrationURL(continueUrl)));
	}

	public static void _logoutUrl(Map<?, ?> args, Closure body, PrintWriter out, ExecutableTemplate template,
			int fromLine) {
		final Object continueParam = args.get("continue");
		String continueUrl = null;

		if (continueParam instanceof ActionDefinition) {
			continueUrl = ((ActionDefinition) continueParam).url;
		} else if (continueParam instanceof String) {
			continueUrl = continueParam.toString();
		} else {
			throw new TemplateExecutionException(template.template, fromLine,
					"Wrong parameter type, try #{loginUrl continue:@Application.index() /}", new TagInternalException(
							"Wrong parameter type"));
		}

		out.print(getUserService().createLogoutURL(continueUrl));
	}
}
