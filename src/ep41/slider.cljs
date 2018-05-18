(ns ep41.slider
  (:require [reagent.core :as r]
            [cljsjs.react-motion]))

(enable-console-print!)

(def Motion js/ReactMotion.Motion)
(def spring js/ReactMotion.spring)

(def toggled? (r/atom false))

(defn motion [{style :style} callback]
  [:> Motion {:style (clj->js style)}
   (fn [style]
     (r/as-element (callback (js->clj style :keywordize-keys true))))])

(defn app []
  [:div
   [:button {:on-mouse-down #(swap! toggled? not)} "Toggle"]
   [motion {:style {:x (spring (if @toggled? 400 0))}}
    (fn [{x :x}]
      [:div.slider
       [:div.slider-block
        {:style {:transform (str "translate3d(" x "px, 0, 0)")}}]])]])

(r/render [app]
          (js/document.getElementById "app"))

