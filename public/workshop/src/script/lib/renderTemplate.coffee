# Die **Render Template Funktion** rendert das übergebene Template und ruft nach erfolgtem
# Render die übergebenen Callback-Funktion auf.

define ['Handlebars'], ->
  'use strict'

  templateCache = {}

  return (templateName, context, callback) ->
    if templateCache[templateName]?
      callback(templateCache[templateName] context)
      return

    $.get "templates/#{templateName}.handlebars", (tpl) ->
      template = Handlebars.compile(tpl);
      templateCache[templateName] = template
      callback(template context)