import { makeStyles } from '@material-ui/core/styles';

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
    root: {
        display: 'flex'
    },
    appBar: {
        zIndex: theme.zIndex.drawer + 1,
        color: 'white',
        background: 'rgba(0, 0, 0, 0.87)'
    },
    spanRedColor: {
        color: '#ff3366'
    },
    drawer: {
        width: drawerWidth,
        flexShrink: 0,
    },
    drawerPaper: {
        width: drawerWidth,
        background: 'rgba(0, 0, 0, 0.87)',
        // backgroundImage: "url('./drawer4.jpg')",
        // backgroundRepeat: "no-repeat",
        // backgroundPosition: "top",
        // backgroundSize: "cover",
        // backgroundAttachment: "fixed",
        height: "100%"
    },
    drawerContainer: {
        overflow: 'auto',
        color: 'white'
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing(3),
        marginLeft: drawerWidth,
        width: '100%',
    },
    menuButton: {
        marginRight: theme.spacing(2),
    },
    title: {
        flexGrow: 1,
    },
    drawerIcon: {
        color: "white"
    },
    paper: {
        marginTop: theme.spacing(3),
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
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1),
    },
    selectEmpty: {
        marginTop: theme.spacing(2),
    },
}));

export default useStyles;