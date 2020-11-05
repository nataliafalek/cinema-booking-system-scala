import {makeStyles} from '@material-ui/core/styles';

const drawerWidth = 240;
//TODO porzadki w css
const useStyles = makeStyles((theme) => ({
    root: {
        display: 'flex'
    },
    cinemaRoot: {
        background: '#030104',
    },
    paprykLogo: {
        width: 200,
        height: 100
    },
    paprykLogoButton: {
        display: "block",
        width: 100
    },
    appBar: {
        zIndex: theme.zIndex.drawer + 1,
        background: '#030104',
        height: '100px'
    },
    drawer: {
        width: drawerWidth,
        flexShrink: 0,
    },
    drawerPaper: {
        width: drawerWidth,
        background: '#030104',
        height: "100%"
    },
    drawerContainer: {
        overflow: 'auto',
        color: 'white'
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing(5),
        marginLeft: drawerWidth,
        width: '100%',
    },
    menuButton: {
        marginRight: theme.spacing(5),
    },
    menuTab1: {
        color: '#B30089',
        fontSize: 20
    },
    menuTab2: {
        color: '#AF00F5',
        fontSize: 20
    },
    tabLabel: {
        fontSize: 20
    },
    title: {
        flexGrow: 1,
    },
    drawerIcon: {
        color: "white"
    },
    paper: {
        marginTop: theme.spacing(5),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(3),
    },
    loginRoot: {
        height: '100vh',
    },
    loginImage: {
        backgroundImage: "url('./drawer10.jpg')",
        backgroundRepeat: 'no-repeat',
        backgroundColor:
            theme.palette.type === 'light' ? theme.palette.grey[50] : theme.palette.grey[900],
        backgroundSize: 'cover',
        backgroundPosition: 'center',
    },
    loginPaper: {
        margin: theme.spacing(8, 4),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    loginAvatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    loginForm: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
    table: {
        minWidth: 650,
    },
    button: {
        margin: theme.spacing(1)
    },
    formControl: {
        width: '100%',
        marginTop: theme.spacing(1),
    },
    selectEmpty: {
        marginTop: theme.spacing(2),
    },
    textField: {
        margin: theme.spacing(3),
        width: 200,
    },
    movieCarousel: {
        marginTop: theme.spacing(10)
    },
    petlaImage: {
        height: 700,
        backgroundImage: "url('./petla.jpg')",
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        opacity: '0.60'
    },
    badboyImage: {
        height: 700,
        backgroundImage: "url('./badboy.jpg')",
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
        backgroundPosition: 'top',
        opacity: '0.60'
    },
    cinemaImage: {
        height: 700,
        backgroundImage: "url('./background1.jpg')",
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        opacity: '0.60'
    },
    marathonImage: {
        height: 700,
        backgroundImage: "url('./background2.jpg')",
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        opacity: '0.60'
    },

    movieCarouselHeader: {
        fontSize: 60,
        position: "absolute",
        top: 10,
        right: 200,
        color: '#B30089'
    },
    movieCarouselText: {
        fontSize: 26,
        position: "absolute",
        top: 140,
        left: '60%',
        color: '#D9E6FC'
    },
    paprykRoot: {
        maxWidth: 345,
    },
    whatsOn: {
        marginTop: theme.spacing(12),
        // color: "white",
        color: '#AF00F5',
        fontSize: 40

    },
    whatsOnTab: {
        fontSize: 40

    },
    whatsOnTabs: {
        backgroundColor: '#030104',
        color: '#AF00F5',
        fontSize: 40
    },
    movieCardMedia: {
        opacity: '0.8',
        height: 0,
        paddingTop: '56.25%', // 16:9
    },
    movieCardContent: {
        height: '100%',
        width: '30%',
        display: 'block',
        marginLeft: 'auto',
        marginRight: 'auto',
        flexDirection: 'column',
        background: '#030104',
    },
    movieCardMediaTitle: {
        color: '#B30089'
    },
    movieCardMediaHour: {
        color: '#AF00F5',
        fontSize: 16
    },
    noRepertoire: {
        backgroundColor: '#030104',
        height: 200,
        color: '#B30089',
        padding: theme.spacing(5),
        textAlign: 'center',
        fontSize: 40
    },
    checkoutAppBar: {
        position: 'relative',
    },
    checkoutLayout: {
        width: 'auto',
        minHeight: 800,
        marginTop: theme.spacing(12),
        marginLeft: theme.spacing(2),
        marginRight: theme.spacing(2),
        [theme.breakpoints.up(600 + theme.spacing(2) * 2)]: {
            width: 1000,
            marginLeft: 'auto',
            marginRight: 'auto',
        },
        opacity: '0.9'
    },
    checkoutPaper: {
        marginTop: theme.spacing(3),
        marginBottom: theme.spacing(3),
        padding: theme.spacing(2),
        height: '100%',
        overflow: 'hidden',
        [theme.breakpoints.up(600 + theme.spacing(3) * 2)]: {
            marginTop: theme.spacing(6),
            marginBottom: theme.spacing(6),
            padding: theme.spacing(3),
        },
        backgroundColor: '#030104',
        color: '#B30089',
    },
    checkoutStepper: {
        padding: theme.spacing(3, 0, 5),
        backgroundColor: '#030104',
        color: '#B30089'
    },
    checkoutButtons: {
        display: 'flex',
        justifyContent: 'flex-end',
    },
    cinemaHallViewHeader: {
        fontSize: 20,
        fontWeigh: 'bold',
        color: '#F6F8F8'
    },
    cinemaHallViewForm: {
        // display: 'block',
        color: 'white',
        // marginLeft: 'auto',
        // marginRight: 'auto',
    },
    cinemaHallView: {
        display: "grid",
        marginBottom: theme.spacing(5)
    },
    cinemaHallViewScreen: {
        height: 25,
        width: 450,
        background: '#ADB5BD',
        color: '#030104',
        display: 'block',
        marginLeft: 'auto',
        marginRight: 'auto',
        marginBottom: 40,
        textAlign: 'center',
        padding: 5
    },
    cinemaHallRow: {
        float: "left",
        display: 'block',
        marginLeft: 'auto',
        marginRight: 'auto',
    },
    cinemaHallSeatReserved: {
        width: 40,
        height: 40,
        background: "#2C003D",
        color: "#2C003D",
        margin: 3,
        display: "inline-block"
    },
    cinemaHallSeatFree: {
        width: 40,
        height: 40,
        background: '#F6F8F8',
        color: "#F6F8F8",
        margin: 3,
        display: "inline-block",
        cursor: 'pointer',

    },
    cinemaHallViewRowNumber: {
        color: '#B30089'
    },
    cinemaHallViewTicket: {
        color: '#F6F8F8'
    },
    footer: {
        backgroundColor: '#030104',
        padding: theme.spacing(6, 0),
        color: '#F6F8F8'
    },
    contentBackground: {
        background: '#030104',
        backgroundImage: "url('./background444.png')",
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
    },
    cardGrid: {
        paddingTop: theme.spacing(8),
        paddingBottom: theme.spacing(8),
    },
    card: {
        height: '100%',
        display: 'flex',
        flexDirection: 'column',
        color: '#F62DAE',
        background: '#030104',
        opacity: '0.8'
    },
    cardMedia: {
        paddingTop: '56.25%', // 16:9
    },
    cardContent: {
        flexGrow: 1,
    },
    movieDetailsContent: {
        marginTop: theme.spacing(12),
        height: 600
    },
    movieDetailsImage: {
        margin: theme.spacing(3),
        height: '70%',
        color: '#F62DAE',
        background: '#030104',
    },
    movieDetailsTitle: {
        marginTop: theme.spacing(3),
        marginBottom: theme.spacing(8),
        color: '#F62DAE',
    },
    movieDetailsDescription: {
        color: 'white',
    },
    whoIsPaprykImage: {
        marginTop: theme.spacing(12),
        padding: theme.spacing(5),
        display: 'block',
        marginLeft: 'auto',
        marginRight: 'auto',
        width: '70%',
        opacity: '0.93'
    },
    personalDataForm: {
        marginTop: theme.spacing(5),
        height: 400
    },
    checkoutSummary: {
        color: '#F6F8F8',
        marginTop: theme.spacing(5),
    }
}));

export default useStyles;