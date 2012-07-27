# Die **Render Template Funktion** rendert das übergebene Template und ruft nach erfolgtem
# Render die übergebenen Callback-Funktion auf.
# Usage: `renderTemplate(templateName, context, callback)`. `templateName` is the path of
# the template starting at `/templates/` with the extension `.handlebars` removed. `context`
# is used to replace placeholders in the template (`{ key: 'value' }). The function 
# `callback` is invoked as soon as the template is loaded and after the placeholders are
# filled. It takes the HTML string as a parameter. The function `errorCallback` is invoked
# if any error occurs (e.g. the requested template could not be found) with the first
# parameter being the templateName and the second parameter being te options element that
# is returned by jQuery.

define ['handlebars', 'underscore'], (Handlebars, _) ->
  'use strict'

  Handlebars.registerHelper 'date', (date) -> new Date(date).toLocaleDateString()

  templateCache = {}

  defaultErrorHandler = (templateName, options) ->
    $(window).trigger('templateLoadError', "received #{options.status} when requesting template #{templateName}")

  return (templateName, context, callback, errorCallback = defaultErrorHandler) ->
    if templateCache[templateName]?
      callback(templateCache[templateName] context)
      return

    $.ajax {
      url: "/templates/#{templateName}.handlebars"
      type: 'GET'
      success: (tpl) ->
        template = Handlebars.compile(tpl)
        templateCache[templateName] = template
        callback(template context)
      error: _.bind errorCallback, this, templateName
    }