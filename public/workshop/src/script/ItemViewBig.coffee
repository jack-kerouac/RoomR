# View für Einträge
# -----------------

#
define ['backbone', 'lib/formatPriority'], (Backbone, formatPriority) -> 

  'use strict'

  return Backbone.View.extend {

    template: _.template('<h3><%= title %></h3>
              <p>(Priorität: <span class="priority"><%= priority %></span>)</p>
              <p>[ <span class="clickable close-item">Schließen</span> ]')

    # Für das Rendering verwenden wir das übergebene Template und hängen das fertige
    # Element direkt in `target` ein.

    render: (target) ->
      target = $(target) unless target instanceof jQuery
      html = @template {
        title: @model.get('title')
        priority: formatPriority @model.get('priority') # Schön formatiert
      }
      @$el.html(html).appendTo(target)
      return this

  }
