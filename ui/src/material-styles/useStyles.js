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
        backgroundImage: "url('./drawer2.jpg')",
        backgroundRepeat: "no-repeat",
        backgroundPosition: "top",
        backgroundSize: "cover",
        backgroundAttachment: "fixed",
        height: "100%"
    },
    drawerContainer: {
        overflow: 'auto',
        color: 'white'
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing(3),
        background: '#fff5f8',
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
    }
}));

export default useStyles;