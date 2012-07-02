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

requirejs.config {
  baseUrl: 'scripts/'
  paths: {
    "lib/modernizr" : "lib/modernizr-2.5.3"
    "lib/jquery-ui" : "lib/jquery-ui-1.8.21.custom.min"
    "lib/underscore" : "lib/underscore-amd"
    "lib/handlebars" : "lib/Handlebars-1.0.beta.6"
    "lib/backbone" : "lib/backbone-amd"
  }
  shim: {
    'lib/backbone' : {
      deps: ['lib/underscore', 'lib/jquery']
      # exports: 'Backbone'
    }
  }
}



require ['base/EventMediator.coffee#','lib/backbone','base/AppRouter.coffee#','sections/PhotoUploadSection.coffee#','sections/OfferRoomSection.coffee#','sections/SearchSection.coffee#','sections/MainSection.coffee#','sections/RegisterSection.coffee#','widgets/LoginWidget.coffee#'],\
(EventMediator, Backbone, AppRouter, PhotoUploadSection, OfferRoomSection, SearchSection, MainSection, RegisterSection, LoginWidget) ->

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

  registerSection = new RegisterSection()
  app.addSection registerSection

  photoUploadSection = new PhotoUploadSection()
  app.addSection photoUploadSection


  # App starten
  # -----------

  $(document).ready ->
    Backbone.history.start( { pushState: true } )
