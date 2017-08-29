module Main exposing (..)

import Html exposing (..)
import Navigation
import Router
import Overview


-- PROGRAM


main : Program Never Model Msg
main =
    Navigation.program OnLocationChange
        { init = init
        , view = view
        , update = update
        , subscriptions = subscriptions
        }


init : Navigation.Location -> ( Model, Cmd Msg )
init location =
    let
        currentRoute =
            Router.parseLocation location

        ( newPage, cmd ) =
            toPage currentRoute
    in
        ( initialModel newPage, cmd )


subscriptions : Model -> Sub Msg
subscriptions model =
    Sub.none



-- MODELS


type Page
    = Overview Overview.Model


type Msg
    = OnLocationChange Navigation.Location
    | OverviewMsg Overview.Msg


type alias Model =
    { page : Page
    }


initialModel : Page -> Model
initialModel page =
    { page = page
    }



-- UPDATE


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case ( msg, model.page ) of
        ( OnLocationChange location, _ ) ->
            let
                newRoute =
                    Router.parseLocation location

                ( newPage, cmd ) =
                    toPage newRoute
            in
                ( { model | page = newPage }, cmd )

        ( OverviewMsg overviewMsg, Overview subModel ) ->
            let
                ( updatedModel, cmd ) =
                    (Overview.update overviewMsg subModel)
            in
                ( { model | page = Overview updatedModel }, Cmd.map OverviewMsg cmd )



{-
   _ ->
       ( Debug.log "No match !!" model, Cmd.none )

-}


toPage : Router.Route -> ( Page, Cmd Msg )
toPage route =
    case route of
        Router.Overview ->
            let
                ( model, cmd ) =
                    Overview.init
            in
                ( Overview model, Cmd.map OverviewMsg cmd )

        Router.MovieDetail id ->
            ( Overview Overview.initialModel, Cmd.map OverviewMsg Overview.initialCmd )



-- VIEWS


view : Model -> Html Msg
view model =
    case model.page of
        Overview subModel ->
            Overview.view subModel |> Html.map OverviewMsg
