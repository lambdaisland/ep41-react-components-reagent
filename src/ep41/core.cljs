(ns ep41.core
  (:require [reagent.core :as r]))

(r/render [:div "hello, venus"] (js/document.getElementById "app"))
