package com.heveamobile.mapbystep.ui.common

import androidx.compose.runtime.Composable
import com.heveamobile.mapbystep.domain.repository.FilePickerHandler

@Composable
expect fun FilePickerHandlerEffect(handler: FilePickerHandler)
