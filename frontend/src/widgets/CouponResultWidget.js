import * as React from 'react';
import Paper from '@material-ui/core/Paper/Paper';
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import Divider from "@material-ui/core/Divider";
import IconButton from "@material-ui/core/IconButton";
import DeleteIcon from '@material-ui/icons/Delete';
import AppBar from "@material-ui/core/AppBar";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogActions from "@material-ui/core/DialogActions";


class CouponResultWidget extends React.Component {

    handleChange = (event, value) => {
        this.setState({value});
    };

    handleClickOpen = () => {
        this.setState({open: true});
    };

    handleClose = () => {
        this.setState({open: false});
    };

    constructor(props, context) {
        super(props, context);

        this.state = {
            value: this.props.coupon.combination ? 1 : 0,
            coupon: this.props.coupon
        };
    }

    componentWillReceiveProps(nextProps) {
        console.log('componentWillReceiveProps', nextProps);

        this.setState({
            value: nextProps.coupon.combination ? 1 : 0,
            coupon: nextProps.coupon
        });
    }

    render() {
        return (
            <Paper>
                <div>
                    <AppBar position="static">
                        <Tabs value={this.state.value} onChange={this.handleChange}>
                            <Tab label="Singles"/>
                            <Tab label="Combination"/>
                        </Tabs>
                    </AppBar>
                    {this.state.coupon.outcomes && this.state.value === 0 && <div>
                        <List component="nav">
                            {this.state.coupon.outcomes.map(x =>
                                ([<ListItem>
                                    <table width="100%">
                                        <tr>
                                            <td align={"left"} width="5%">
                                                <IconButton aria-label="Delete">
                                                    <DeleteIcon fontSize="small"/>
                                                </IconButton>
                                            </td>
                                            <td align={"left"} width="50%">
                                                <Paper>
                                                    <div>{x.name}</div>
                                                    <div>{x.betOfferType}</div>
                                                    <a>{x.eventName}</a>
                                                </Paper>
                                            </td>
                                            <td align={"right"}>
                                                <div>{x.odds}</div>
                                                {/*<Button fullWidth variant="contained" color="primary">*/}
                                                {/*Replace*/}
                                                {/*</Button>*/}
                                            </td>
                                        </tr>
                                    </table>
                                </ListItem>,
                                    <Divider/>])
                            )}
                        </List>

                        <table width="100%">
                            <tr>
                                <td>Stake:</td>
                                <td align={"right"}>{this.state.coupon.totalStake}</td>
                            </tr>
                            <tr>
                                <td>Total Payout:</td>
                                <td align={"right"}>{this.state.coupon.totalPayout}</td>
                            </tr>
                            <tr>
                                <td>
                                    {/*<Button variant="contained">*/}
                                    {/*Get another coupon*/}
                                    {/*</Button>*/}
                                </td>
                                <td align={"right"}>
                                    <Button variant="contained" color="primary" onClick={this.handleClickOpen}>
                                        Place Bet
                                    </Button>
                                </td>
                            </tr>
                        </table>

                        <Dialog open={this.state.open} onClose={this.handleClose} aria-labelledby="alert-dialog-title"
                                aria-describedby="alert-dialog-description">
                            <DialogContent>
                                <DialogContentText id="alert-dialog-description">
                                    Bet successfully placed!
                                </DialogContentText>
                            </DialogContent>
                            <DialogActions>
                                <Button onClick={this.handleClose} color="primary" autoFocus>
                                    Close
                                </Button>
                            </DialogActions>
                        </Dialog>
                    </div>
                    }
                </div>
                {this.state.coupon.outcomes && this.state.value === 1 && <div>
                    <List component="nav">
                        {this.state.coupon.outcomes.map(x =>
                            ([<ListItem>
                                <table width="100%">
                                    <tr>
                                        <td align={"left"} width="5%">
                                            <IconButton aria-label="Delete">
                                                <DeleteIcon fontSize="small"/>
                                            </IconButton>
                                        </td>
                                        <td align={"left"} width="50%">
                                            <Paper>
                                                <div>{x.name}</div>
                                                <div>{x.betOfferType}</div>
                                                <a>{x.eventName}</a>
                                            </Paper>
                                        </td>
                                        <td align={"right"}>
                                            <div>{x.odds}</div>
                                            {/*<Button fullWidth variant="contained" color="primary">*/}
                                            {/*Replace*/}
                                            {/*</Button>*/}
                                        </td>
                                    </tr>
                                </table>
                            </ListItem>,
                                <Divider/>])
                        )}
                    </List>

                    <table width="100%">
                        <tr>
                            <td>Stake:</td>
                            <td align={"right"}>{this.state.coupon.totalStake}</td>
                        </tr>
                        <tr>
                            <td>Total Odds:</td>
                            <td align={"right"}>{this.state.coupon.totalOdds}</td>
                        </tr>
                        <tr>
                            <td>Total Payout:</td>
                            <td align={"right"}>{this.state.coupon.totalPayout}</td>
                        </tr>
                        <tr>
                            <td>
                                {/*<Button variant="contained">*/}
                                {/*Get another coupon*/}
                                {/*</Button>*/}
                            </td>
                            <td align={"right"}>
                                <Button variant="contained" color="primary" onClick={this.handleClickOpen}>
                                    Place Bet
                                </Button>
                            </td>
                        </tr>
                    </table>

                    <Dialog open={this.state.open} onClose={this.handleClose} aria-labelledby="alert-dialog-title"
                            aria-describedby="alert-dialog-description">
                        <DialogContent>
                            <DialogContentText id="alert-dialog-description">
                                Bet successfully placed!
                            </DialogContentText>
                        </DialogContent>
                        <DialogActions>
                            <Button onClick={this.handleClose} color="primary" autoFocus>
                                Close
                            </Button>
                        </DialogActions>
                    </Dialog>
                </div>}

                {!this.state.coupon.outcomes && <Paper>Can not generate coupon from input params!</Paper>}
            </Paper>
        );
    }
}

export default CouponResultWidget;