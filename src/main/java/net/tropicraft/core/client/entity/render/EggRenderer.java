package net.tropicraft.core.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.tropicraft.core.client.TropicraftRenderUtils;
import net.tropicraft.core.client.entity.model.EggModel;
import net.tropicraft.core.common.entity.egg.EggEntity;

public class EggRenderer extends LivingRenderer<EggEntity, EggModel> {

	public EggRenderer(final EntityRendererManager rendererManager) {
		super(rendererManager, new EggModel(), 1f);
		shadowOpaque = 0.5f;
	}

	@Override
	public void render(EggEntity egg, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
		stack.push();
		if (egg.shouldEggRenderFlat()) {
			shadowSize = 0.0f;
			drawFlatEgg(egg, 0, 0.05f, 0, stack, bufferIn);
		} else {
			shadowSize = 0.2f;
			stack.scale(0.5f, 0.65f, 0.5f);
			super.render(egg, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
		}
		stack.pop();
	}
	
	public void drawFlatEgg(EggEntity ent, double x, double y, double z, MatrixStack stack, IRenderTypeBuffer bufferIn) {
		stack.push();

		stack.translate(x, y, z);
		stack.rotate(this.renderManager.getCameraOrientation());
		stack.rotate(Vector3f.YP.rotationDegrees(180.0F));

		stack.scale(0.25f, 0.25f, 0.25f);

		RenderSystem.normal3f(0.0F, 1.0F, 0.0F);
		final IVertexBuilder buffer = TropicraftRenderUtils.getEntityCutoutBuilder(bufferIn, getEntityTexture(ent));
		buffer.pos(-.5, -.25, 0).tex(0, 1).endVertex();
		buffer.pos( .5, -.25, 0).tex(1, 1).endVertex();
		buffer.pos( .5,  .75, 0).tex(1, 0).endVertex();
		buffer.pos(-.5,  .75, 0).tex(0, 0).endVertex();

		stack.pop();
	}
	
	@Override
	public ResourceLocation getEntityTexture(EggEntity entity) {
		return TropicraftRenderUtils.bindTextureEntity(entity.getEggTexture());
	}
}