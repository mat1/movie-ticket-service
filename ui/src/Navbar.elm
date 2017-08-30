module Navbar exposing (..)

import Html exposing (..)
import Html.Events exposing (onClick, on, onInput, onMouseOver)
import Html.Attributes as Attr exposing (id, class, classList, src, name, type_, title, href, rel, attribute, placeholder)


navbar : Maybe (String -> msg) -> Html msg
navbar msg =
    nav [ class "navbar navbar-dark bg-dark" ]
        [ a [ class "navbar-brand", href "#", id "logo" ] [ text "Movie Ticket Service" ]
        , showSearchField msg
        ]


showSearchField : Maybe (String -> msg) -> Html msg
showSearchField maybeMsg =
    case maybeMsg of
        Nothing ->
            text ""

        Just msg ->
            form [ class "form-inline" ]
                [ input [ attribute "aria-label" "Search", class "form-control", placeholder "Search", type_ "text", onInput msg ] []
                ]
