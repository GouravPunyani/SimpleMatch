// @flow

import type { UserStore } from '../../../stores/user-store'
import type { NavigationStore } from '../../../stores/navigation-store'
import { Dimensions } from 'react-native'
import { inject, observer } from 'mobx-react'
import React, { Component } from 'react'
import { StaticLayout, Button, ButtonPanel, Text, DashboardCarousel } from '../..'
import { translator } from '../../../services/translate/translator'
import { palette, spacing } from '../../theme'
const { width, height } = Dimensions.get('window')

export type Props = {
  userStore: UserStore,
  navigationStore: NavigationStore,
  translate: (key: string) => string
};

@translator
@inject('userStore', 'navigationStore')
@observer
export class DashboardScreen extends Component<*, Props, *> {
  textForLoggingIn () {
    const { translate, userStore } = this.props
    const { isLoggedIn, user } = userStore
    const { email, fetching } = user

    if (fetching) return translate('dashboard.loggingIn')
    return isLoggedIn ? email : translate('dashboard.notLoggedIn')
  }

  render () {
    const { navigationStore, translate } = this.props
    return (
      <StaticLayout style={styles.wrapper}>
        <DashboardCarousel/>
        <ButtonPanel>
          <Button
            text={translate('dashboard.getStarted')}
            onPress={() => navigationStore.navigateTo('product')}
            style={styles.button}
          />
        </ButtonPanel>
      </StaticLayout>
    )
  }
}

const styles = {
  wrapper: { flex:1, padding: spacing.none },
  button: {
    position: 'absolute',
    left: width*.05,
    right: width*.05,
    bottom: height*.15
  }
}
