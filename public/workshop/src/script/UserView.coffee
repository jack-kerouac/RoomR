# View für Einträge
# -----------------

#
define ['backbone', 'base/renderTemplate'], (Backbone, renderTemplate) -> 

  'use strict'

  return Backbone.View.extend {
    # Da Einträge könnten recht vielfältig verwendet werden können, richten wir uns auf
    # flexible Nutzung von Templates ein.
    initialize: (options) ->
      @template = options.template

    # Für das Rendering verwenden wir das übergebene Template und hängen das fertige
    # Element direkt in `target` ein.
    render: (target) ->
      target = $(target) unless target instanceof jQuery
      context = {
        id: @model.get('id')
        email: @model.get('email')
        name: @model.get('name')
      }
      renderTemplate "user/#{@template}", context, (html) =>
        @$el.html(html).appendTo(target)

      return this
  }
