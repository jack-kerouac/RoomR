# Die **Render Template Funktion** rendert das übergebene Template und ruft nach erfolgtem
# Render die übergebenen Callback-Funktion auf.
# Usage: `renderTemplate(templateName, context, callback)`. `templateName` is the path of
# the template starting at `/templates/` with the extension `.handlebars` removed. `context`
# is used to replace placeholders in the template (`{ key: 'value' }). The function 
# `callback` is invoked as soon as the template is loaded and after the placeholders are
# filled. It takes the HTML string as a parameter.

define ['Handlebars'], ->
  'use strict'

  templateCache = {}

  return (templateName, context, callback) ->
    if templateCache[templateName]?
      callback(templateCache[templateName] context)
      return

    $.get "/templates/#{templateName}.handlebars", (tpl) ->
      template = Handlebars.compile(tpl);
      templateCache[templateName] = template
      callback(template context)