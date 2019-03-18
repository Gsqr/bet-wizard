import React from 'react';
import ReactDOM from 'react-dom';
import { MuiThemeProvider, CssBaseline } from '@material-ui/core';
import { sparkCyan } from '@kambi/spark-ui/themes';
import App from './App';
import * as serviceWorker from './serviceWorker';

const AppComponent = (
	<MuiThemeProvider theme={sparkCyan}>
		<CssBaseline />
		<App />
	</MuiThemeProvider>
);

ReactDOM.render(AppComponent, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
