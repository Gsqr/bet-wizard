/* eslint-disable react/jsx-no-target-blank*/
import React from 'react';
import {Body, Footer, Header, HeaderToolbar, Layout} from '@kambi/spark-ui/scaffolding';
import {withStyles} from '@material-ui/core';
import BetWizard from './widgets/BetWizard';

const styles = {
	logo: {
		animation: 'rotate infinite 10s linear',
		height: '6rem',
		margin:'2rem',
	},
	headerText: {
		justifyContent: 'center'
	},
	header: {
		marginBottom: '1rem',
	},
	paper: {
		textAlign: 'center'
	},
    compact: {
        overflowY: 'auto',
        padding: '0.5em',
        paddingTop: 0,
        flex: 1
    },
	'@keyframes rotate': {
		from: {
			transform: 'rotate(0deg)'
		},
		to: {
			transform: 'rotate(360deg)'
		}
	}
};

const App = ({classes})  => (
	<Layout variant="big-header">
		<Header variant="main">
			<HeaderToolbar variant="upper">
				Bet Wizard
			</HeaderToolbar>
		</Header>
        <Body variant='compact' classes={{paper: classes.paper, compact: classes.compact}} display='flex'>
            <BetWizard/>
		</Body>
		<Footer>
			Kambi AB
		</Footer>
	</Layout>
);

export default withStyles(styles)(App);
