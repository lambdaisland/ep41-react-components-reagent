



;; React component -> Reagent component
(reagent.core/adapt-react-class react-component)
[:> react-component ,,,]



;; Hiccup -> React element
(reagent.core/as-element [:p "hiccup"])



;; Reagent component -> React component
(reagent.core/reactify-component)



;; React element -> Hiccup
use directly

