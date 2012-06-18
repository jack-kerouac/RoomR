# View für Einträge
# -----------------

#
define ['backbone', 'lib/formatPriority'], (Backbone, formatPriority) -> 

  'use strict'

  return Backbone.View.extend {

    # **Tipp:** In CoffeeScript hat Multiline-Strings
    templates: {
      list: '<span id="list-item-<%= id %>" class="item-title"><%= title %></span>
              (Priorität: <span class="priority"><%= priority %></span>)'
      full: '<h3><%= title %></h3>
              <p>(Priorität: <span class="priority"><%= priority %></span>)</p>
              <p>[ <span class="clickable close-item">Schließen</span> ]'
    }

    # Da Einträge könnten recht vielfältig verwendet werden können, richten wir uns auf
    # flexible Nutzung von Templates ein. Falls `options.template` gesetzt ist, prüfen
    # wir, ob der Wert ein Key von `@templates` ist. Falls das der Fall ist, wird dessen
    # Wert verwendet, andernfalls behandeln wir den übergebenen String selbst als
    # Template. Das Template kompilieren wir in eine Funktion, die wir am Ende nur noch
    # aufrufen müssen.
    initialize: (options) ->
      if options.template
        tpl = @templates[options.template] if options.template of @templates
        @template = _.template(tpl)

    # Für das Rendering verwenden wir das übergebene Template und hängen das fertige
    # Element direkt in `target` ein.
    render: (target) ->
      target = $(target) unless target instanceof jQuery
      html = @template {
        id: @model.get('id')
        title: @model.get('title')
        priority: formatPriority @model.get('priority') # Schön formatiert
      }
      @$el.html(html).appendTo(target)
      return this

  }
