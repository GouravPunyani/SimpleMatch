// @flow

import React, { Component } from 'react'
import { inject, observer } from 'mobx-react'
import { StaticLayout, ButtonPanel, Button, ScanInfo } from '../..'
import { bind } from 'decko'
import { scan } from '../../../lib/scan'
import { translator } from '../../../services/translate/translator'
import { ScanStore } from '../../../stores/scan-store'

export type Props = {
  translate: (key: string) => string,
  scanStore: ScanStore
};

@translator
@inject('scanStore')
@observer
export class ScanHomeScreen extends Component<*, Props, *> {
  @bind async startScan () {
    await scan()
  }

  render () {
    const { translate, scanStore } = this.props
    const { scan } = scanStore
    return (
      <StaticLayout>
        <ButtonPanel>
          <Button
            text={translate('scanScreen.startScan')}
            onPress={this.startScan}
          />
          <ScanInfo scan={scan} />
        </ButtonPanel>

      </StaticLayout>
    )
  }
}
