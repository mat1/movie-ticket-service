module MovieDetail exposing (..)

import MovieApi exposing (..)
import Http
import Html exposing (..)
import Html.Events exposing (onClick, on, onInput, onMouseOver)
import Html.Attributes as Attr exposing (id, class, classList, src, name, type_, title, href, rel, attribute, placeholder)


-- MODEL


type alias Model =
    { selectedMovie : Maybe MovieDetail
    }


init : Int -> ( Model, Cmd Msg )
init id =
    ( initialModel, (MovieApi.loadMovie id LoadMovie) )


initialModel : Model
initialModel =
    { selectedMovie = Nothing }



-- UPDATE


type Msg
    = LoadMovie (Result Http.Error MovieDetail)


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        LoadMovie (Ok movieDetail) ->
            ( { model | selectedMovie = Just movieDetail }, Cmd.none )

        LoadMovie (Err err) ->
            ( model, Cmd.none )



-- VIEW


view : Model -> Html Msg
view model =
    div [ class "container-fluid" ]
        [ navbar
        , div [ class "row" ] [ viewMovieDetail model.selectedMovie ]
        ]


emptyNode : Html Msg
emptyNode =
    text ""


viewMovieDetail : Maybe MovieDetail -> Html Msg
viewMovieDetail movieDetail =
    case movieDetail of
        Nothing ->
            emptyNode

        Just detail ->
            div []
                [ div [ class "col" ] [ img [ src detail.poster ] [] ]
                , div [ class "col" ]
                    [ div [ class "movie-details" ]
                        [ h4 [] [ text detail.title ]
                        , span [] [ text detail.plot ]
                        ]
                    ]
                ]


navbar : Html Msg
navbar =
    nav [ class "navbar navbar-dark bg-dark" ]
        [ a [ class "navbar-brand", href "#", id "logo" ] [ text "Movie Ticket Service" ]
        ]
