# Fast die gesamte Beispielapp steckt in dieser Datei. Die aus den Kommentaren generierte
# Dokumentation liegt im `docs`-Verzeichnis. Die Kommentare können mit
# [Markdown](http://daringfireball.net/projects/markdown/) formatiert werden.


# Abhängigkeiten
# --------------

# Die `require()`-Funktion erwartet zwei Argumente: erstens ein Array mit den
# Abhängigkeiten für dieses Script und zweitens die Callback-Funktion, die ausgeführt
# wird, sobald alle Abhängigkeiten geladen wurden. Wenn die Abhängigkeiten selbst
# AMD-Module sind und Objekte zurückgeben, werden diese an den Callback als Argumente
# übergeben. Das ist nicht immer der Fall -- das `backbone`-Modul hat z.B. jQuery und
# [Underscrore.js](http://underscorejs.org/) Abhängigkeiten und lädt diese auch. Da
# je doch diese beiden Libraries  ihre APIs (`$` und `_`) als globale Objekte
# bereitstellen, können wir beides in unserer Callback-Funkion verwenden, ohne sie dort
# im Callback explizit aufzuführen.

require [
  'base/EventMediator',
  'backbone',
  'base/AppRouter',
  'sections/PhotoUploadSection',
  'sections/OfferRoomSection',
  'sections/SearchSection',
  'sections/MainSection',
  'widgets/LoginWidget'],
(EventMediator,
  Backbone,
  AppRouter,
  PhotoUploadSection,
  OfferRoomSection,
  SearchSection,
  MainSection,
  LoginWidget) ->

  'use strict'

  # Router anwerfen
  app = new AppRouter()

  # handle login separately. do not add it to a section but render it straight into an element
  loginWidget = new LoginWidget()
  loginWidget.renderInto($('#login'))

  mainSection = new MainSection()
  app.addSection mainSection

  searchSection = new SearchSection()
  app.addSection searchSection

  offerRoomSection = new OfferRoomSection()
  app.addSection offerRoomSection

  photoUploadSection = new PhotoUploadSection()
  app.addSection photoUploadSection


  # App starten
  # -----------

  $(document).ready ->
    Backbone.history.start( { pushState: true } )
