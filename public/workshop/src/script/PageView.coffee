# View für Pages
# --------------

# Views sind für den Output von Daten verantwortlich. Man kann sie direkt mit Models
# verzahnen, muss das aber nicht tun. Eigentlich *muss* man gar nichts - Views sind sehr
# flexibel und es gibt nicht viele Vorgaben. Dieser PageView dient dazu, den Inhalt der
# Hauptspalte `#main` neu zu rendern.
define ['backbone'], (Backbone) ->
  
  'use strict'

  return  Backbone.View.extend {

    # Jeder View repräsentiert ein HTML-Element. Das kann entweder ein bereits vorhandenes
    # Element in der Seite sein oder es kann kann auch erst vom View erzeugt werden. In
    # diesem Fall ist das Element für diesen View (View-Eigenschaft `el`) ein Element in
    # der Seite - daher der Selektor hier.
    el: '#Main'

    # `render()` ist die View-Methode für die Ausgabe. Standardmäßig macht `render()` gar
    # nichts, d.h. man *muss* für jeden View eine eigene Methode schreiben. Diese Variante
    # nimmt einen Titel und Inhalt an und befüllt damit die entsprechenden Elemente.
    render: (title, html) ->
      $('#Headline').html title                   # Überschrift setzen
      # **Tipp:** In Strings mit " gibt es String-Interpolation mit `#{variable}`
      $('title').text("Beispiel-App - #{title}")  # `<title>` setzen
      # **Tipp:** `$el` in Views ist ein jQuery-Objekt mit dem `el` des Views

      @$el.html(html)             # Das Element des Views mit Inhalt befüllen
      return this
    
  }
