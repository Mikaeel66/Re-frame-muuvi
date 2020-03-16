(ns kehys.views
  (:require
   [re-frame.core :as rf]
   [kehys.subs :as subs]
   [tick.alpha.api :as t]))

(defn main-panel []
  (let [categories  (rf/subscribe [::subs/categories]) muuvi (rf/subscribe [::subs/muuvi])]

    [:div {:style {:display "flex"}}
      [:div {:style {:width 150}}
            (for [item @categories]
               (let [name (get item :name ) 
                     c_id (get item :category_id)]
                [:div
                 [:input {:type  "checkbox"
                          :id c_id
                          :on-click #(rf/dispatch [:valinta])}]
                 [:b name]]))]

      [:div {:style {:flex 1}}
        [ :input  {:on-change #(rf/dispatch [:value-change (-> % .-target .-value)])}]
        [:table   
          (for [item @muuvi]
            (let [name (get item :title) 
                  dest (get item :description)]
                [:tr
                 [:td name]
                 [:td dest] ]))]]]))
