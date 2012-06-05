package templates;

import groovy.lang.Closure;

import java.io.PrintWriter;
import java.util.Map;

import play.exceptions.TagInternalException;
import play.exceptions.TemplateExecutionException;
import play.mvc.Router.ActionDefinition;
import play.templates.FastTags;
import play.templates.GroovyTemplate.ExecutableTemplate;

public class CustomTags extends FastTags {

	public static void _loginUrl(Map<?, ?> args, Closure body, PrintWriter out,
			ExecutableTemplate template, int fromLine) {
		final Object continueParam = args.get("continue");
		String continueUrl = null;

		if (continueParam instanceof ActionDefinition) {
			continueUrl = ((ActionDefinition) continueParam).url;
		} else if (continueParam instanceof String) {
			continueUrl = continueParam.toString();
		} else {
			throw new TemplateExecutionException(
					template.template,
					fromLine,
					"Wrong parameter type, try #{loginUrl continue:@Application.index() /}",
					new TagInternalException("Wrong parameter type"));
		}

		out.print(controllers.Registration.createRegistrationURL(continueUrl));
	}

	public static void _logoutUrl(Map<?, ?> args, Closure body,
			PrintWriter out, ExecutableTemplate template, int fromLine) {
		final Object continueParam = args.get("continue");
		String continueUrl = null;

		if (continueParam instanceof ActionDefinition) {
			continueUrl = ((ActionDefinition) continueParam).url;
		} else if (continueParam instanceof String) {
			continueUrl = continueParam.toString();
		} else {
			throw new TemplateExecutionException(
					template.template,
					fromLine,
					"Wrong parameter type, try #{loginUrl continue:@Application.index() /}",
					new TagInternalException("Wrong parameter type"));
		}

		out.print(continueUrl);
	}
}
