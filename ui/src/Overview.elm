module Overview exposing (..)

import Html exposing (..)
import Html.Events exposing (onClick, on, onInput, onMouseOver)
import Html.Attributes as Attr exposing (id, class, classList, src, name, type_, title, href, rel, attribute, placeholder)
import MovieApi exposing (..)
import Http
import Navbar


-- MODEL


type alias Model =
    { movies : List Movie
    , loadedMovies : List Movie
    , loadingError : Maybe String
    , selectedMovie : Maybe MovieDetail
    }


init : ( Model, Cmd Msg )
init =
    ( initialModel, (MovieApi.loadMovies LoadMovies) )


initialModel : Model
initialModel =
    { movies = [], loadedMovies = [], loadingError = Nothing, selectedMovie = Nothing }



-- UPDATE


type Msg
    = FilterMovies String
    | SelectMovie Int
    | LoadMovies (Result Http.Error (List Movie))
    | LoadMovie (Result Http.Error MovieDetail)


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        FilterMovies title ->
            ( filterMovies title model, Cmd.none )

        SelectMovie id ->
            ( model, (MovieApi.loadMovie id LoadMovie) )

        LoadMovies (Ok movies) ->
            ( { model | loadedMovies = movies, movies = movies }, Cmd.none )

        LoadMovies (Err err) ->
            ( { model | loadingError = Just (toString err) }, Cmd.none )

        LoadMovie (Ok movieDetail) ->
            ( { model | selectedMovie = Just movieDetail }, Cmd.none )

        LoadMovie (Err err) ->
            ( model, Cmd.none )


filterMovies : String -> Model -> Model
filterMovies title model =
    { model | movies = List.filter (\m -> String.contains (String.toLower title) (String.toLower m.title)) model.loadedMovies }



-- VIEW


viewOrError : Model -> Html Msg
viewOrError model =
    case model.loadingError of
        Just error ->
            text error

        Nothing ->
            view model


view : Model -> Html Msg
view model =
    div [ class "container-fluid" ]
        [ Navbar.navbar (Just FilterMovies)
        , div [ class "row" ] (List.map (viewMovie model.selectedMovie) model.movies)
        ]


viewMovie : Maybe MovieDetail -> Movie -> Html Msg
viewMovie movieDetail movie =
    div [ class "col" ]
        [ div [ class "movie" ]
            [ a [ href ("#movie/" ++ (toString movie.id)) ]
                [ img [ class "poster", src movie.poster, onMouseOver (SelectMovie movie.id) ] []
                , viewMovieDetail movieDetail movie.id
                ]
            ]
        ]


emptyNode : Html Msg
emptyNode =
    text ""


viewMovieDetail : Maybe MovieDetail -> Int -> Html Msg
viewMovieDetail movieDetail selectedId =
    case movieDetail of
        Nothing ->
            emptyNode

        Just detail ->
            if detail.id == selectedId then
                div [ class "movie-details" ]
                    [ h4 [] [ text detail.title ]
                    , span [] [ text detail.plot ]
                    ]
            else
                emptyNode



-- SUBSCRIPTIONS


subscriptions : Model -> Sub Msg
subscriptions model =
    Sub.none
