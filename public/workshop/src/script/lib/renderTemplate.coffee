# Die **Render Template Funktion** rendert das übergebene Template und ruft nach erfolgtem
# Render die übergebenen Callback-Funktion auf.

define ['Handlebars'], ->
  'use strict'

  return (templateName, context, callback) ->
    $.get "templates/#{templateName}.handlebars", (tpl) ->
      template = Handlebars.compile(tpl);
      callback(template context)