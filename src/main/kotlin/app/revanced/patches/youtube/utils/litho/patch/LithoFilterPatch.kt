package app.revanced.patches.youtube.utils.litho.patch

import app.revanced.extensions.toErrorResult
import app.revanced.patcher.annotation.Version
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patches.shared.annotation.YouTubeCompatibility
import app.revanced.patches.shared.patch.litho.ComponentParserPatch
import app.revanced.patches.shared.patch.litho.ComponentParserPatch.Companion.generalHook
import app.revanced.patches.youtube.utils.fix.doublebacktoclose.patch.DoubleBackToClosePatch
import app.revanced.patches.youtube.utils.fix.swiperefresh.patch.SwipeRefreshPatch
import app.revanced.patches.youtube.utils.litho.fingerprints.ByteBufferFingerprint
import app.revanced.patches.youtube.utils.playertype.patch.PlayerTypeHookPatch
import app.revanced.util.integrations.Constants.ADS_PATH

@DependsOn(
    [
        ComponentParserPatch::class,
        DoubleBackToClosePatch::class,
        PlayerTypeHookPatch::class,
        SwipeRefreshPatch::class
    ]
)
@YouTubeCompatibility
@Version("0.0.1")
class LithoFilterPatch : BytecodePatch(
    listOf(ByteBufferFingerprint)
) {
    override fun execute(context: BytecodeContext): PatchResult {


        ByteBufferFingerprint.result?.mutableMethod?.addInstruction(
            0,
            "sput-object p0, $ADS_PATH/ByteBufferFilterPatch;->bytebuffer:Ljava/nio/ByteBuffer;"
        ) ?: return ByteBufferFingerprint.toErrorResult()


        generalHook("$ADS_PATH/LithoFilterPatch;->filters")

        return PatchResultSuccess()
    }
}
