(ns ep41.core
  (:require [reagent.core :as r]
            [cljsjs.react-ultimate-pagination]))

(def state (r/atom {:page 1
                    :page-size 10
                    :rows []}))


(defn page [{:keys [value isActive onClick isDisabled]}]
  [:button {:style (if isActive {:font-weight "bold"}), :on-click onClick, :disabled isDisabled} value])

(defn ellipsis [{:keys [onClick isDisabled]}]
  [:button {:on-click onClick, :disabled isDisabled} "..."])

(defn first-page-link [{:keys [onClick isDisabled]}]
  [:button {:on-click onClick, :disabled isDisabled} "<<"])

(defn last-page-link [{:keys [onClick isDisabled]}]
  [:button {:on-click onClick, :disabled isDisabled} ">>"])

(defn next-page-link [{:keys [onClick isDisabled]}]
  [:button {:on-click onClick, :disabled isDisabled} ">"])

(defn previous-page-link [{:keys [onClick isDisabled]}]
  [:button {:on-click onClick, :disabled isDisabled} "<"])

(defn wrapper [{:keys [children]}]
  [:div {:style {:padding "1em 0"}}
   children])

(def pagination-config
  #js {:itemTypeToComponent
       #js {:PAGE               (r/reactify-component page)
            :ELLIPSIS           (r/reactify-component ellipsis)
            :FIRST_PAGE_LINK    (r/reactify-component first-page-link)
            :LAST_PAGE_LINK     (r/reactify-component last-page-link)
            :NEXT_PAGE_LINK     (r/reactify-component next-page-link)
            :PREVIOUS_PAGE_LINK (r/reactify-component previous-page-link)}
       :WrapperComponent (r/reactify-component wrapper)})

(def pagination
  (r/adapt-react-class
   (.createUltimatePagination js/ReactUltimatePagination pagination-config)))

(defn table [rows]
  [:table
   [:thead
    (into [:tr]
          (for [x ["Title" "Name" "Type" "Origin"]]
            [:th x]))]
   (into [:tbody]
         (for [row rows]
           (into [:tr]
                 (for [cell row]
                   [:td cell]))))])

(defn app []
  (let [{:keys [page page-size rows]} @state]
    [:div {:style {:font-family "sans-serif"}}
     [table (->> rows
                 (drop (* (dec page) page-size))
                 (take page-size))]
     [pagination {:currentPage page
                  :totalPages (max 1 (Math/ceil (/ (count rows) page-size)))
                  :onChange (fn [page]
                              (swap! state assoc :page page))}]]))

(r/render [app] (js/document.getElementById "app"))

(-> (js/fetch "teas.json")
    (.then #(.json %))
    (.then (fn [json]
             (swap! state assoc :rows json))))
