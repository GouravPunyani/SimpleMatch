// @flow

import glam from 'glamorous-native'
import React, { Component } from 'react'
import { View, Text, StyleSheet, TextInput, Dimensions } from 'react-native'
import { inject, observer } from 'mobx-react'
import { Form, StaticLayout } from '../..'
import type { UserStore } from '../../../stores/user-store'
import { translator } from '../../../services/translate/translator'
const { width, height } = Dimensions.get('window')

const Stretchy = glam.view({ height: height*.01 })

@translator
@inject('userStore')
@observer
export default class ChangePasswordScreen extends Component {
  static propTypes: {
  };

  constructor(props) {
    super(props);
    // TODO: This should be taken from information already in the account.
    this.state = {
      password: '',
      confirmation: ''
    };
  }

  render () {
    const { userStore, translate } = this.props
    return (
      // TODO: The form isn't connected to anything. The information isn't being updated. Also needs hidden password functionnality.
      <StaticLayout>
        <Text style={styles.title}>{translate('account.password')}</Text>
        <Form>
          <TextInput
            style={styles.text}
            autoCapitalize={'none'}
            placeholder={'••••••••'}
            onChangeText={(password) => this.setState({password})}
            value={this.state.password}
          /><Stretchy/>
          <TextInput
            style={styles.text}
            autoCapitalize={'none'}
            placeholder={'••••••••'}
            onChangeText={(confirmation) => this.setState({confirmation})}
            value={this.state.confirmation}
          /><Stretchy/>
        </Form>
      </StaticLayout>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: {
    flex: 1,
    backgroundColor: 'white',
    justifyContent: 'center',
    alignItems: 'center'
  },
  title: { color: 'black', fontSize: 14, padding: 7 },
  text: { color: 'dimgray', fontSize: 12, height: height*.045, borderColor: '#dcb8bd', borderWidth: 2, padding: 7}
})
