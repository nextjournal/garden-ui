^{:nextjournal.clerk/visibility {:code :hide}}
(ns nextjournal.garden.ui.home
  {:nextjournal.clerk/visibility {:code :hide :result :hide}
   :nextjournal.clerk/open-graph
   {:url "https://github.clerk.garden"
    :title "Clerk Garden"
    :description "A simple publishing space for Clerk notebooks."
    :image "https://cdn.nextjournal.com/data/QmVFYe7EG8M8ZimiHjp19UUd13SuhCKQoCwxnThnNZ8e6y?filename=clerk-garden-og-image.png&content-type=image/png"}}
  (:require [clojure.string :as str]
            [nextjournal.clerk :as clerk]
            [nextjournal.clerk.viewer :as v]))

(def github-icon
  [:svg {:width "24", :height "24", :viewbox "0 0 24 24", :fill "none", :xmlns "http://www.w3.org/2000/svg"}
   [:path {:fill-rule "evenodd", :clip-rule "evenodd", :d "M11.9989 0C5.37254 0 0 5.5085 0 12.3041C0 17.7401 3.43804 22.3512 8.20651 23.979C8.8069 24.0915 9.02569 23.7116 9.02569 23.3853C9.02569 23.0937 9.01538 22.3195 9.00948 21.2931C5.67163 22.0363 4.96737 19.6434 4.96737 19.6434C4.4215 18.2219 3.63473 17.8435 3.63473 17.8435C2.5452 17.0807 3.71724 17.0958 3.71724 17.0958C4.9217 17.1826 5.55523 18.3639 5.55523 18.3639C6.62562 20.2439 8.36416 19.7009 9.04779 19.3859C9.15682 18.5913 9.46622 18.049 9.80951 17.7416C7.14497 17.4311 4.34341 16.3752 4.34341 11.6605C4.34341 10.3176 4.8112 9.21936 5.57881 8.35906C5.45505 8.04787 5.04325 6.79707 5.69594 5.1029C5.69594 5.1029 6.7037 4.77207 8.99622 6.36427C9.95316 6.09085 10.9801 5.9549 12.0004 5.95036C13.0192 5.9549 14.0461 6.09085 15.0045 6.36427C17.2956 4.77207 18.3011 5.1029 18.3011 5.1029C18.956 6.79707 18.5442 8.04787 18.4205 8.35906C19.1895 9.21936 19.6544 10.3176 19.6544 11.6605C19.6544 16.3873 16.8484 17.4274 14.175 17.7317C14.606 18.1117 14.9898 18.8625 14.9898 20.0105C14.9898 21.6549 14.975 22.9819 14.975 23.3853C14.975 23.7146 15.1909 24.0975 15.8001 23.9774C20.5649 22.3467 24 17.7385 24 12.3041C24 5.5085 18.6267 0 11.9989 0Z", :fill "currentColor"}]])

(def star-icon
  [:svg {:width "24", :height "24", :viewbox "0 0 24 24", :fill "none", :xmlns "http://www.w3.org/2000/svg"}
   [:path {:fill-rule "evenodd", :clip-rule "evenodd", :d "M12 0.375C12.21 0.374864 12.4159 0.433521 12.5943 0.54433C12.7727 0.65514 12.9165 0.813678 13.0095 1.002L15.8325 6.7245L22.1475 7.6425C22.3552 7.67266 22.5503 7.76033 22.7108 7.89558C22.8713 8.03082 22.9907 8.20827 23.0556 8.40785C23.1206 8.60743 23.1284 8.8212 23.0781 9.02498C23.0279 9.22876 22.9217 9.41443 22.7715 9.561L18.2025 14.016L19.281 20.304C19.3166 20.5109 19.2935 20.7236 19.2145 20.9181C19.1355 21.1126 19.0036 21.2811 18.8338 21.4045C18.664 21.528 18.4631 21.6015 18.2537 21.6166C18.0443 21.6318 17.8348 21.5881 17.649 21.4905L12 18.5205L6.351 21.4905C6.16528 21.588 5.956 21.6317 5.74679 21.6165C5.53757 21.6014 5.33676 21.528 5.16703 21.4048C4.99729 21.2815 4.86539 21.1133 4.78622 20.919C4.70706 20.7248 4.68377 20.5123 4.719 20.3055L5.799 14.0145L1.227 9.561C1.07635 9.41448 0.969751 9.2287 0.91928 9.0247C0.868809 8.82071 0.876485 8.60665 0.941439 8.40679C1.00639 8.20693 1.12603 8.02926 1.28679 7.89392C1.44754 7.75857 1.643 7.67095 1.851 7.641L8.166 6.7245L10.9905 1.002C11.0835 0.813678 11.2273 0.65514 11.4057 0.54433C11.5841 0.433521 11.79 0.374864 12 0.375V0.375ZM12 4.0425L9.9225 8.25C9.84183 8.41335 9.72268 8.55467 9.57532 8.66179C9.42795 8.76892 9.25678 8.83865 9.0765 8.865L4.431 9.54L7.791 12.816C7.92173 12.9433 8.01954 13.1005 8.07598 13.274C8.13243 13.4475 8.14582 13.6322 8.115 13.812L7.323 18.438L11.4765 16.254C11.6379 16.1691 11.8176 16.1248 12 16.1248C12.1824 16.1248 12.3621 16.1691 12.5235 16.254L16.6785 18.438L15.8835 13.812C15.8527 13.6322 15.8661 13.4475 15.9225 13.274C15.979 13.1005 16.0768 12.9433 16.2075 12.816L19.5675 9.5415L14.9235 8.8665C14.7432 8.84015 14.5721 8.77042 14.4247 8.66329C14.2773 8.55617 14.1582 8.41485 14.0775 8.2515L12 4.041V4.0425Z", :fill "currentColor"}]])

(def stars-badge
  [:div.py-1.px-2.rounded-full.text-greenish.justify-center.ml-2.flex.items-center.bg-greenish-30.stars-badge
   {:class "h-[22px] min-w-[48px]"}])

(def js-behaviors
  {:transform-fn clerk/mark-presented
   :render-fn '(fn []
                 (v/html
                  [:div
                   {:ref (fn [el]
                           (-> (js/fetch "https://api.github.com/repos/nextjournal/clerk")
                               (.then (fn [res] (.json res)))
                               (.then (fn [data]
                                        (if-let [stars (.-stargazers_count data)]
                                          (j/assoc! (js/document.querySelector ".stars-badge") :innerHTML stars)
                                          (throw (js/Error. "stargazers_count missing in response data")))))
                               (.catch (fn [e]
                                         (js/console.error e)
                                         (.. (js/document.querySelector ".stars-badge") -classList (add "hidden"))))))}]))})

(defn code-listing [text]
  [:pre.text-sm.p-6.text-greenish.overflow-x-auto
   {:class "bg-[#93BD9A]/20"}
   text])

(defn code [text]
  [:code.font-iosevka.text-greenish-60.p-0.bg-transparent
   {:style {:font-size "inherit"}}
   text])

(defn step [indicator md]
  (clerk/html
   [:div.flex.items-center.font-sans.font-bold.step
    [:div.mr-2.rounded-full.border-2.border-green-800.bg-green-100.flex.items-center.justify-center.text-lg.leading-none
     {:class "w-[34px] h-[34px]"}
     indicator]
    (clerk/md md)]))

{::clerk/visibility {:result :show}}

(clerk/with-viewer js-behaviors nil)

(clerk/html
 [:<>
  [:style {:type "text/css"}
   ":root {
     --greenish: rgba(146, 189, 154, 1);
     --greenish-60: rgba(146, 189, 154, 0.6);
     --greenish-50: rgba(146, 189, 154, 0.5);
     --greenish-30: rgba(146, 189, 154, 0.3)
   }
   html { overflow-y: auto !important; }
   body { background: #000 !important; font-family: 'Inter', sans-serif; color: var(--greenish); }
   .scroll-container { height: auto !important; }
   #clerk-static-app > div { background: #000 !important; height: auto !important; }
   #clerk-static-app .viewer-notebook > :first-child { display: none; }
   .dark-mode-toggle { display: none; }
   a { color: var(--greenish); transition: all 0.125s ease;}
   a:hover { color: white; }
   .viewer-notebook { padding: 0; }
   .viewer-result { margin: 0; }
   .viewer-result + .viewer-result { margin: 0; }
   .font-iosevka { font-family: 'Iosevka Web', monospace; }
   .font-inter { font-family: 'Inter', sans-serif; }
   .text-greenish { color: var(--greenish); }
   .text-greenish-60 { color: var(--greenish-60); }
   .bg-greenish { background-color: var(--greenish); }
   .bg-greenish-30 { background-color: var(--greenish-30); }
   .border-greenish-50 { border: 4px solid var(--greenish-30); }
   .separator-top { border-top: 4px solid var(--greenish-50); }
   .separator-bottom { border-bottom: 4px solid var(--greenish-50); }
   .section-heading { border-top: 4px solid var(--greenish-50); }
   .link-hairline { border-bottom: 1px solid var(--greenish-60); }
   .link-hairline:hover { border-color: white; }
   .twitter-card iframe { border: 3px solid var(--greenish-30); border-radius: 15px; overflow: hidden; margin-top: -10px;"]
  [:link {:rel "preconnect" :href "https://fonts.bunny.net"}]
  [:link {:rel "stylesheet" :href "https://fonts.bunny.net/css?family=inter:400,600"}]
  [:link {:rel "preconnect" :href "https://ntk148v.github.io"}]
  [:link {:rel "stylesheet" :href "https://ntk148v.github.io/iosevkawebfont/latest/iosevka.css"}]])

^{::clerk/width :full}
(clerk/html
 [:div.px-8.lg:px-0.md:mx-auto.not-prose
  {:class "lg:max-w-[1024px]"}
  [:nav.separator-bottom.pt-12.pb-4.text-sm.flex.justify-end.md:justify-between.page-scroll-links.relative
   [:ul.flex.absolute.md:relative.left-0
    {:class "-bottom-[30px] md:bottom-0"}
    [:li.mr-4 [:a {:href "https://clerk.vision"} "What is Clerk? ↗"]]
    [:li.mr-4 [:a {:href "https://github.clerk.garden/nextjournal/book-of-clerk/"} "The Book of Clerk ↗"]]]
   [:ul.flex
    [:li.mr-4
     [:a.flex.items-center {:href "http://github.com/nextjournal/clerk"} github-icon [:span.ml-2.hidden.md:inline "GitHub"]]]
    [:li [:a.flex.items-center {:href "http://github.com/nextjournal/clerk"} star-icon [:span.ml-2.hidden.md:inline "Star on GitHub"] stars-badge]]]]])

^{::clerk/width :full}
(v/with-viewer
  {:render-fn
   '(fn []
      (v/html
       (let [navigate-to-repo! (fn [] (let [input (.getElementById js/document "build-input")
                                            repo (.-value input)
                                            path (str "/" repo "?update=1")]
                                        (set! (.-location js/window) path)))]
         [:div.px-8.lg:px-0.md:mx-auto.not-prose
          {:class "lg:max-w-[1024px]"}
          [:div.mt-20.text-greenish.font-iosevka.flex.items-end.justify-between
           [:div.w-full.flex-auto
            [:h1.flex [:img {:src "https://cdn.nextjournal.com/data/QmTmkRBotM198V8Uvrvp2F1rMLAcHLmbfH1tTcg64J749G?filename=garden-logo.svg&content-type=image/svg%2Bxml" :alt "Clerk Garden"}]]
            [:h2.text-xl.md:text-2xl.font-medium.mt-6.font-iosevka
             "A simple publishing space for Clerk notebooks."]
            [:p.text-xl.md:text-2xl.font-light.mt-2
             "Enter a GitHub repo to build and publish."]
            [:div.flex.items-center.mt-6
             [:input#build-input.flex-auto.px-4.md:text-lg.border-0.rounded-none.focus:text-white.focus:ring.focus:ring-2.focus:ring-white.outline-none.transition-all.duration-350
              {:class "h-[52px] bg-[#93BD9A]/20 placeholder:text-[#9BBC9D]/30"
               :placeholder "e.g. nextjournal/clerk-demo"
               :on-key-down (fn [e] (when (= "Enter" (.-key e)) (navigate-to-repo!)))}]
             [:button.iosevka.font-medium
              {:type "submit"
               :class "h-[52px] w-[60px] bg-[#93BD9A]/30 text-[#9BBC9D] hover:bg-[#93BD9A]/60 hover:text-white transition-all duration-350"
               :on-click navigate-to-repo!}
              "⮐"]]]
           [:figure.flex-auto.ml-10.text-center.hidden.lg:flex.flex-col.items-end.flex-shrink-0
            [:img {:src "https://cdn.nextjournal.com/data/QmRnuZkxmyuUeZ8ZxCd7y74Ux6caDmLBqBK3rMWwvUc6kB?filename=tree.svg&content-type=image/svg%2Bxml" :width 248}]]]
          [:p.pt-3.text-sm.font-inter.text-greenish-60 "or click here to try an example: " [:a {:href "https://github.clerk.garden/nextjournal/clerk-demo/"} "nextjournal/clerk-demo"]]])
       [:div.text-center.not-prose.font-sans]))} nil)

^{::clerk/width :full}
(clerk/html
 [:div.px-8.lg:px-0.md:mx-auto.not-prose
  {:class "lg:max-w-[1024px]"}
  [:div.mt-16
   [:h2.section-heading.pt-4.text-sm
    [:span.font-iosevka.font-medium.uppercase.text-greenish "How does it work?"]]
   [:div.mt-10.text-greenish
    [:h3.text-lg.md:text-2xl.font-medium.mt-6.font-iosevka.mb-0
     "1. Add a " (code ":nextjournal.clerk") " to " (code ":aliases") " in your " (code "deps.edn") " file:"]
    (code-listing
     "{:deps ...
 :aliases
 {:nextjournal/clerk
  {:exec-fn nextjournal.clerk/build!
   :exec-args {:paths [\"notebooks/hello_garden.clj\"]}
   ;; TODO: add all notebooks you want to have built ☝️
   ;; if you just want to build one notebook, set `:index`

   ;; optional list of aliases to include in the build
   :nextjournal.clerk/aliases [:dev]}}}")
    [:h3.text-lg.md:text-2xl.font-medium.mt-6.font-iosevka.mb-0.mt-10
     "2. Try it out locally to make sure it works"]
    [:p.font-inter.mt-4.mb-6.max-w-xl.leading-normal.text-greenish-60
     "Entering the following command into your terminal should build your notebooks and open an index page in your browser."]
    (code-listing "clj -X:nextjournal/clerk :browse true")
    [:p.font-inter.mt-4.mb-6.max-w-xl.leading-normal.text-greenish-60
     "Prepend any extra aliases: " (code "clj -X:dev:nextjournal/clerk")]
    [:h3.text-lg.md:text-2xl.font-medium.mt-6.font-iosevka.mb-0
     "3. Push it to GitHub and done!"]
    [:p.font-inter.mt-4.max-w-xl.leading-normal.text-greenish-60
     "Clerk Garden mirrors your GitHub URLs, so:"]
    [:ul.font-inter.mt-4.mb-6.max-w-2xl.leading-normal.text-greenish-60.list-disc.px-4
     [:li "after pushing your project to " [:a {:href "https://github.com/your-handle/your-project"} "https://github.com/your-handle/your-project"] ","]
     [:li "you can simply visit " [:a {:href "https://github.clerk.garden/your-handle/your-project"} "https://github.clerk.garden/your-handle/your-project"] " and we'll build it for you or redirect you to the last successful build! ✨"
      [:br]
      [:span.text-xs "(to force an update for an already built project, append " (code "?update=1") ")"]]]]]
  [:footer.text-sm.my-20.text-greenish-60.separator-top.pt-4
   [:ul.flex
    [:li.mr-4 "This website is built with Clerk."]]]])
