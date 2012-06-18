# Model für Pages
# ---------------

# Als `Page` wird in diesem Beispiel der Inhalt der Hauptspalte bezeichnet. Ein
# **Model** hierfür erstellen wir, indem wir von der Model-Vorlage `Backbone.Model`
# über die **`extend()`-Methode** ein Abziehbild erstellen und unsere eigenen
# Eigenschaften hineinschreiben. Wir können einerseits vorhandene Eigenschaften und
# Methoden überschreiben (z.B. `initialize`), andererseits auch komplett eigene
# Eigenschaften festlegen (z.B. `show`).

define ['backbone'], (Backbone) ->
  
  'use strict'
  
  return Backbone.Model.extend {

    # Wir überschreiben die normale (nichts machende) `initialize()`-Methode, um zu Beginn
    # immer `start` als Page zu verwenden
    initialize: ->
      # **Tipp:** `@` ist CoffeeScript das gleiche wie `this.`
      @set {
        page: 'start'
      }

    # Eine eigene Methode, die nichts weiter macht als auf komfortable Art und Weise die
    # Werte `page` und `id` zu setzen. Bei `id` aufpassen, dass es eine Number ist - der
    # Parameter kommt als String aus der URL an
    show: (page, id) ->
      @set {
        page: page
        id: Number id
      }

  }
